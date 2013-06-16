describe("un WebSocket", function() {

    it("test", function() {
        var res = [];
        var x;
        runs(function() {
            x = new PT.CONTROLLER.S.WebSocket("wss://echo.websocket.org");
            x.invia({quaranta: 2});
            x.addEvent("aperto", function() {
                x.invia({ciccia: "bubu"});
            });
            x.addEvent("messaggio", function(msg) {
                res.push(msg);
            });
            x.addEvent("chiuso", function() {
                res = 'chiuso';
            });
        });

        waitsFor(function() {
            return (res[0] && res[0].quaranta === 2);
        }, null, 10000);

        waitsFor(function() {
            return (res[1] && res[1].ciccia === 'bubu');
        }, null, 10000);

        runs(function() {
            x.chiudi();
        });

        waitsFor(function() {
            return (res === 'chiuso');
        }, null, 10000);
    });
});
