describe("il RegistroCanali", function() {

    it("test", function() {
        var inst = PT.CONTROLLER.S.getInstanceRegistroCanali();
        expect(PT.CONTROLLER.S.getInstanceRegistroCanali()).toEqual(inst);

        expect(inst.getCanaleVideo()).toBe(null);

        var ultima = -42;
        for (var i = 0; i < 10; i++) {
            var corrente = inst.getPortaLibera();
            expect(corrente).toBeGreaterThan(ultima);
            ultima = corrente;
        }

        var c = {};
        var porta = inst.getPortaLibera();
        inst.aggiungi(porta, c);
        inst.setCanaleVideo(porta);
        expect(inst.getCanaleVideo()).toBe(porta);

        expect(window.onbeforeunload()).toBeTruthy();

        expect(inst.cerca(porta)).toBe(c);
        inst.elimina(porta);
        expect(inst.cerca(porta)).toBeFalsy();
        expect(inst.getCanaleVideo()).toBe(null);
    });
});