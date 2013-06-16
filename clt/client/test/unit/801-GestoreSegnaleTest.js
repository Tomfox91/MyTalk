describe("GestoreSegnale", function() {

    var rcan = PT.CONTROLLER.S.getInstanceRegistroCanali;
    var ch = jasmine.createSpyObj('ch', ['ricevi']);
    var orig_AV = PT.CONTROLLER.S.CanaleAV;
    var orig_T = PT.CONTROLLER.S.CanaleT;

    beforeEach(function() {
        PT.CONTROLLER.S.getInstanceRegistroCanali = function() {
            return {
                cerca: function(d) {
                    if (d === 42) {
                        return ch;
                    }
                }
            };
        };
        PT.CONTROLLER.S.CanaleAV = function() {
            return ch;
        };
        spyOn(PT.CONTROLLER.S, "CanaleAV").andCallThrough();
        PT.CONTROLLER.S.CanaleT = function() {
            return ch;
        };
        spyOn(PT.CONTROLLER.S, "CanaleT").andCallThrough();
    });

    afterEach(function() {
        PT.CONTROLLER.S.getInstanceRegistroCanali = rcan;
        PT.CONTROLLER.S.CanaleAV = orig_AV;
        PT.CONTROLLER.S.CanaleAV = orig_T;
    });

    it("generico", function() {
        new PT.CONTROLLER.G.GestoreSegnale().esegui({
            mittente: "dest",
            porta: 42,
            sottotipo: "tipo",
            segnale: {
                a: 42
            }
        });

        expect(ch.ricevi).toHaveBeenCalledWith({
            mittente: "dest",
            porta: 42,
            sottotipo: "tipo",
            segnale: {
                a: 42
            }
        });
    });

    it("nuovo video", function() {
        new PT.CONTROLLER.G.GestoreSegnale().esegui({
            mittente: "dest",
            porta: 0,
            sottotipo: "RichiestaAggiunta",
            segnale: {
                tipo: "video"
            }
        });

        expect(PT.CONTROLLER.S.CanaleAV).toHaveBeenCalledWith();
        expect(ch.ricevi).toHaveBeenCalledWith({
            mittente: "dest",
            porta: 0,
            sottotipo: "RichiestaAggiunta",
            segnale: {
                tipo: "video"
            }
        });
    });

    it("nuovo data", function() {
        new PT.CONTROLLER.G.GestoreSegnale().esegui({
            mittente: "dest",
            porta: 0,
            sottotipo: "RichiestaAggiunta",
            segnale: {
                tipo: "data"
            }
        });

        expect(PT.CONTROLLER.S.CanaleT).toHaveBeenCalledWith();
        expect(ch.ricevi).toHaveBeenCalledWith({
            mittente: "dest",
            porta: 0,
            sottotipo: "RichiestaAggiunta",
            segnale: {
                tipo: "data"
            }
        });
    });
});
