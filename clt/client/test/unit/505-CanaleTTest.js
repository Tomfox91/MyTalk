describe("CanaleT", function() {

    var orig_PT = PT;

    var dispatcher;
    var socket;
    var updater;
    var rcan;
    var registro;
    var creatore;
    var proprioID = 'id@ids.com';
    var accettaRichiesta;
    var canaleVideo;
    var a, b;

    beforeEach(function() {
        PT = Object.clone(orig_PT);
        accettaRichiesta = true;
        canaleVideo = null;
        socket = {};
        dispatcher = {
            getSocket: function() {
                return {
                    invia: socket.invia
                };
            }
        };
        PT.CONTROLLER.getInstanceDispatcher = function() {
            return dispatcher;
        };

        rcan = jasmine.createSpyObj('regCon',
                ['aggiungi', 'elimina', 'setCanaleVideo']);
        rcan.getPortaLibera = function() {
            return 42;
        };
        rcan.getCanaleVideo = function() {
            return canaleVideo;
        };
        spyOn(rcan, 'getPortaLibera').andCallThrough();
        spyOn(rcan, 'getCanaleVideo').andCallThrough();
        PT.CONTROLLER.S.getInstanceRegistroCanali = function() {
            return rcan;
        };

        updater = {
            richiestaChiamata: function(o) {
                o.callback(accettaRichiesta);
            }
        };
        updater.notificaEsito = jasmine.createSpy('notificaEsito');
        spyOn(updater, 'richiestaChiamata').andCallThrough();
        PT.VIEW.getInstanceUpdater = function() {
            return updater;
        };

        creatore = {
            creaFinestra: function() {
                var o = jasmine.createSpyObj('windowObject',
                        ['ricevi', 'rimuovi', 'eliminaUtente', 'addEvent']);
                o.rimuoviStream = function() {
                    return true;
                };
                spyOn(o, 'rimuoviStream').andCallThrough();
                return o;
            }
        };
        spyOn(creatore, 'creaFinestra').andCallThrough();
        PT.VIEW.getCreatore = function() {
            return creatore;
        };

        registro = {
            getProprioNome: function() {
                return proprioID;
            },
            getProprioId: function() {
                return proprioID;
            }
        };
        PT.MODEL.getInstanceRegistro = function() {
            return registro;
        };

        RTCMultiConnection = function() {
            return jasmine.createSpyObj('connection',
                    ['open', 'connect', 'leave', 'send']);
        };

        jasmine.Clock.useMock();


        var canali = {};

        socket.invia = function(data) {
            var dest = canali[data.evento.destinatario];
            data.evento.destinatario = undefined;
            data.evento.mittente = 'a@q.com';
            data = Object.clone(data);
            dest.ricevi(data.evento);
        };
        a = canali['a@q.com'] = new PT.CONTROLLER.S.CanaleT();

        socket.invia = function(data) {
            var dest = canali[data.evento.destinatario];
            data.evento.destinatario = undefined;
            data.evento.mittente = 'b@q.com';
            data = Object.clone(data);
            dest.ricevi(data.evento);
        };
        b = canali['b@q.com'] = new PT.CONTROLLER.S.CanaleT();

    });

    afterEach(function() {
        PT = orig_PT;
    });

    describe('comunicazione', function() {

        var f, ach, bch, arx, brx;
        beforeEach(function() {
            a.richiediAggiuntaDestinatario('b@q.com');
            expect(a.getDestinatari()).toEqual(['b@q.com']);
            expect(a.getInAttesa()).toEqual([]);

            f = jasmine.createSpy('f');

            ach = a.connection.openSignalingChannel({
                channel: '42',
                onmessage: function(x) {
                    arx.push(x);
                },
                onopen: f,
                getStats: function(x) {
                    x({
                        timestamp: new Date(),
                        bytesSent: 84
                    });
                }
            });

            bch = b.connection.openSignalingChannel({
                channel: '42',
                onmessage: function(x) {
                    brx.push(x);
                }
            });
            arx = [];
            brx = [];
        });

        describe('onopen', function() {
            beforeEach(function() {
                a.connection.onopen();
            });

            it('onmessaggio', function() {

                a.windowObject.addEvent.calls[1].args[1](42);
                expect(a.connection.send).toHaveBeenCalledWith(
                        {mittente: 'id@ids.com', messaggio: 42});
            });

            it('onchiudi', function() {
                a.windowObject.addEvent.calls[0].args[1]();
                expect(a.connection.send).toHaveBeenCalledWith(
                        {leaving: 'id@ids.com'});
                expect(a.connection.leave).toHaveBeenCalled();
                expect(a.windowObject.rimuovi).toHaveBeenCalled();
                expect(a.windowObject.rimuovi).toHaveBeenCalled();
                expect(rcan.elimina).toHaveBeenCalled();
            });

            describe('onmessage', function() {
                it('norm', function() {
                    a.connection.onmessage(42);
                    expect(a.windowObject.ricevi).toHaveBeenCalledWith(42);
                });

                it('onchiudi', function() {
                    a.connection.onmessage({leaving: '4@due.com'});
                    expect(a.windowObject.eliminaUtente).
                            toHaveBeenCalledWith('4@due.com');
                });
            });
        });
    });
});
