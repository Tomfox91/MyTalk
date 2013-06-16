describe("il Dispatcher", function() {

    it("test", function() {
        var dis = PT.CONTROLLER.getInstanceDispatcher();
        expect(PT.CONTROLLER.getInstanceDispatcher()).toBe(dis);
        var e;
        dis.aggiungiGestore("Finto", {
            esegui: function(evt) {
                e = evt;
            }
        });

        dis.socket.fireEvent('messaggio', {tipo: "Finto", evento: {a: 42}});
        expect(e).toEqual({a: 42});

        expect(dis.getSocket().invia).not.toBeFalsy();
    });
});
