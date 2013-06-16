describe("CanaleAV", function() {

    var orig_PT = PT;

    var dispatcher;
    var socket;
    var updater;
    var rcan;
    var registro;
    var creatore;
    var proprioID;
    var accettaRichiesta;
    var canaleVideo;
    var a, b, c;

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
                        ['rimuovi', 'setStatistiche', 'addEvent', 'setDestinatari']);
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
        proprioID = 'a@q.com';
        a = canali['a@q.com'] = new PT.CONTROLLER.S.CanaleAV('video');

        socket.invia = function(data) {
            var dest = canali[data.evento.destinatario];
            data.evento.destinatario = undefined;
            data.evento.mittente = 'b@q.com';
            data = Object.clone(data);
            dest.ricevi(data.evento);
        };
        proprioID = 'b@q.com';
        b = canali['b@q.com'] = new PT.CONTROLLER.S.CanaleAV();

        socket.invia = function(data) {
            var dest = canali[data.evento.destinatario];
            data.evento.destinatario = undefined;
            data.evento.mittente = 'c@q.com';
            data = Object.clone(data);
            dest.ricevi(data.evento);
        };
        proprioID = 'c@q.com';
        c = canali['c@q.com'] = new PT.CONTROLLER.S.CanaleAV();

    });

    afterEach(function() {
        PT = orig_PT;
    });

    it("errore tipo", function() {
        expect(function() {
            new PT.CONTROLLER.S.CanaleAV('cicciabubu');
        }).toThrow();
    });

    it("già in comunicazione", function() {
        canaleVideo = 42;
        expect(function() {
            new PT.CONTROLLER.S.CanaleAV('video');
        }).toThrow();
    });

    it('getIdCanale', function() {
        expect(a.getIdCanale()).toBe(42);
    });

    it('rifiuto utente', function() {
        accettaRichiesta = false;
        a.richiediAggiuntaDestinatario('b@q.com');
        expect(rcan.elimina.calls.length).toBe(2);
    });

    it('rifiuto per occupato', function() {
        canaleVideo = 42;
        a.richiediAggiuntaDestinatario('b@q.com');
        expect(rcan.elimina.calls.length).toBe(2);
    });

    it("apertura", function() {
        a.richiediAggiuntaDestinatario('b@q.com');
        expect(a.getDestinatari()).toEqual(['b@q.com']);

        expect(b.connection.connect).toHaveBeenCalled();
        expect(b.connection.open).not.toHaveBeenCalled();
        expect(a.connection.connect).not.toHaveBeenCalled();
        expect(a.connection.open).toHaveBeenCalled();

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

            jasmine.Clock.tick(2);
            expect(f).toHaveBeenCalled();

            bch = b.connection.openSignalingChannel({
                channel: '42',
                onmessage: function(x) {
                    brx.push(x);
                }
            });
            arx = [];
            brx = [];
        });

        it('setCanaleVideo', function() {
            expect(rcan.setCanaleVideo.calls.length).toBe(2);
        });

        describe('a due', function() {
            it('tx #0', function() {
                ach.send({ciccia: 'bubu'});
                expect(arx).toEqual([]);
                expect(brx).toEqual([{ciccia: 'bubu'}]);
            });

            it('tx #1', function() {
                bch.send({quaranta: 'due'});
                expect(arx).toEqual([{quaranta: 'due'}]);
                expect(brx).toEqual([]);
            });
        });

        describe('a tre', function() {
            var cch, crx;
            beforeEach(function() {
                b.richiediAggiuntaDestinatario('c@q.com');

                cch = c.connection.openSignalingChannel({
                    channel: '42',
                    onmessage: function(x) {
                        crx.push(x);
                    }
                });
                crx = [];
            });

            it('tx #0', function() {
                ach.send({ciccia: 'bubu'});
                expect(arx).toEqual([]);
                expect(brx).toEqual([{ciccia: 'bubu'}]);
                expect(crx).toEqual([{ciccia: 'bubu'}]);
            });

            it('tx #1', function() {
                bch.send({ciccia: 'bubu'});
                expect(arx).toEqual([{ciccia: 'bubu'}]);
                expect(brx).toEqual([]);
                expect(crx).toEqual([{ciccia: 'bubu'}]);
            });

            it('tx #2', function() {
                cch.send({ciccia: 'bubu'});
                expect(arx).toEqual([{ciccia: 'bubu'}]);
                expect(brx).toEqual([{ciccia: 'bubu'}]);
                expect(crx).toEqual([]);
            });

            it('filtro beacon', function() {
                for (var i = 0; i < 30; i++) {
                    bch.send({sessionid: 42, userid: 42});
                }
                expect(crx.length).toBe(3);
            });

            describe('abbandona', function() {
                beforeEach(function() {
                    c.abbandona();
                });

                it('rcan elimina 1', function() {
                    expect(rcan.elimina.calls.length).toBe(1);
                });

                it('tx', function() {
                    ach.send({ciccia: 'bubu'});
                    expect(arx).toEqual([]);
                    expect(brx).toEqual([{ciccia: 'bubu'}]);
                });

                it('rcan elimina 3', function() {
                    b.abbandona();
                    expect(rcan.elimina.calls.length).toBe(3);
                });
            });
        });

        describe('gestione stream', function() {
            describe('onstream locale audio', function() {
                beforeEach(function() {
                    a.connection.onstream({
                        type: 'local',
                        session: {
                            isAudio: function() {
                                return true;
                            }
                        },
                        mediaElement: 42
                    });
                });

                it('creaFinestra', function() {
                    expect(creatore.creaFinestra).toHaveBeenCalledWith({
                        elem: 42,
                        locale: true,
                        id: '_media_local',
                        canale: 42,
                        tipo: 'audio'
                    });
                });

                describe('chiudi', function() {
                    beforeEach(function() {
                        a.chiudi();
                    });

                    it('Canale chiuso', function() {
                        expect(rcan.elimina).toHaveBeenCalled();
                    });
                });
            });

            describe('onstream remoto', function() {
                beforeEach(function() {
                    a.connection.onstream({
                        type: 'remote',
                        session: {
                            isAudio: function() {
                                return false;
                            }
                        },
                        userid: '42',
                        mediaElement: 42
                    });
                });

                it('creaFinestra', function() {
                    expect(creatore.creaFinestra).toHaveBeenCalledWith({
                        elem: 42,
                        locale: false,
                        id: '_media_42',
                        canale: 42,
                        tipo: 'video'
                    });
                });

                it('contStream', function() {
                    expect(a.contStream).toBe(1);
                });

                it('onchiudi', function() {
                    a.chiudi = jasmine.createSpy('chiudi');
                    a.windowObject.addEvent.calls[0].args[1]();
                    expect(a.chiudi).toHaveBeenCalled();
                });

                it('onaggiungi', function() {
                    a.richiediAggiuntaDestinatario =
                            jasmine.createSpy('richiediAggiuntaDestinatario');
                    a.windowObject.addEvent.calls[1].args[1]('4@due.it');
                    expect(a.richiediAggiuntaDestinatario).
                            toHaveBeenCalledWith('4@due.it');
                });

                it('onRichiestaDestinatari', function() {
                    a.windowObject.addEvent.calls[2].args[1]();
                    expect(a.windowObject.setDestinatari).
                            toHaveBeenCalledWith(['b@q.com']);
                });

                describe('statistiche', function() {

                    it('attivo', function() {
                        a.stats.roundTrip = 42;
                        jasmine.Clock.tick(5000);
                        expect(a.windowObject.setStatistiche).toHaveBeenCalled();
                    });

                    it('passivo #1', function() {
                        var d = new Date();
                        a.connection.onmessage({ping: d, id: 'a@q.com'});
                        expect(a.connection.send).
                                toHaveBeenCalledWith({pong: d, id: 'a@q.com'});
                    });

                    it('passivo #2', function() {
                        var d = new Date();
                        a.connection.onmessage({pong: d, id: 'a@q.com'});
                        expect(a.stats.roundTrip).toBeLessThan(5);
                    });
                });

                describe('altro onstream remoto', function() {
                    beforeEach(function() {
                        a.connection.onstream({
                            type: 'remote',
                            session: {
                                isAudio: function() {
                                    return false;
                                }
                            },
                            userid: '43',
                            mediaElement: 43
                        });
                    });

                    it('contStream', function() {
                        expect(a.contStream).toBe(2);
                    });

                    describe('onleave', function() {
                        beforeEach(function() {
                            a.connection.onleave(42);
                        });

                        it('rimuoviStream', function() {
                            expect(a.windowObject.rimuoviStream).
                                    toHaveBeenCalledWith('_media_42');
                        });

                        it('contStream', function() {
                            expect(a.contStream).toBe(1);
                        });

                        describe('altro onleave', function() {
                            beforeEach(function() {
                                a.connection.onleave(43);
                            });

                            it('rimuoviStream', function() {
                                expect(a.windowObject.rimuoviStream).
                                        toHaveBeenCalledWith('_media_43');
                            });

                            it('contStream', function() {
                                expect(a.contStream).toBe(0);
                            });

                            it('chiudi', function() {
                                expect(a.connection.leave).toHaveBeenCalled();
                                expect(a.windowObject.rimuovi).toHaveBeenCalled();
                            });
                        });

                        describe('chiudi', function() {
                            beforeEach(function() {
                                a.chiudi();
                            });

                            it('chiusure', function() {
                                expect(a.connection.leave).toHaveBeenCalled();
                                expect(a.windowObject.rimuovi).toHaveBeenCalled();
                            });

                            it('Canale ancora aperto', function() {
                                expect(rcan.elimina).not.toHaveBeenCalled();
                            });

                            describe('altro onleave', function() {
                                beforeEach(function() {
                                    a.connection.onleave(43);
                                });

                                it('rimuoviStream', function() {
                                    expect(a.windowObject.rimuoviStream).
                                            toHaveBeenCalledWith('_media_43');
                                });

                                it('contStream', function() {
                                    expect(a.contStream).toBe(0);
                                });

                                it('Canale chiuso', function() {
                                    expect(rcan.elimina).toHaveBeenCalled();
                                });
                            });
                        });
                    });
                });
            });
        });
    });
});
