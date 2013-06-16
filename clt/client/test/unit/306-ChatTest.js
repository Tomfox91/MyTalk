describe("Chat", function() {
    var orig_PT = PT;
    var view;
    var orig_Date = Date;
    var data = new Date();
    beforeEach(function() {
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');
        PT.VIEW.getInstanceSubjectView = function() {
            view = jasmine.createSpyObj("view", ["fireEvent"]);
            return view;

        };

        ut = new PT.MODEL.Utente("e@ma.il", "Online", "nome")
        ut2 = new PT.MODEL.Utente("e2@ma.il", "Offline", "nome2");
        $("#chat a[href='#destinatari'] .label").hide();
        $("#chat a");
        Date.time = function() {
            return data;
        };
        $("#chat a").remove();
    });
    afterEach(function() {
        PT = orig_PT;
        Date = orig_Date;
    });
    it("ricevi", function() {
        var chat = new PT.VIEW.Chat({
            canale: 'Canale',
            destinatari: 'destinatari',
            tipo: 'data'
        });
        chat.ricevi({mittende: "mittente", messaggio: "messaggio"});
        expect($("#destinatari")).toHaveText("mittente: messaggio");
    });
    it("riceviSuChatNonAttiva", function() {
        var chat = new PT.VIEW.Chat({
            canale: 'Canale',
            destinatari: 'destinatari',
            tipo: 'data'
        });
        var chat2 = new PT.VIEW.Chat({
            canale: 'Canale2',
            destinatari: 'destinatari2',
            tipo: 'data'
        });

        chat.ricevi({mittende: "mittente", messaggio: "messaggio"});
        expect($("#destinatari")).toHaveText("mittente: messaggio");
        $("#chat a[href='#destinatari']").trigger("click");
        $("#destinatari" + data.getTime() + "_form button").click();
    });

    it("rimuovi", function() {
        var chat = new PT.VIEW.Chat({
            canale: 'Canale',
            destinatari: 'destinatari',
            tipo: 'data'
        });
        var chat2 = new PT.VIEW.Chat({
            canale: 'Canale2',
            destinatari: 'destinatari2',
            tipo: 'data'
        });
        chat.rimuovi();
        expect($("#chat li").length).toBe(1);
        expect($("#chat li")).toHaveClass("active");
    });
    it("rimuovi2", function() {
        var chat = new PT.VIEW.Chat({
            canale: 'Canale',
            destinatari: 'destinatari',
            tipo: 'data'
        });
        var chat2 = new PT.VIEW.Chat({
            canale: 'Canale2',
            destinatari: 'destinatari2',
            tipo: 'data'
        });
        chat2.rimuovi();
        expect($("#chat li").length).toBe(1);
        expect($("#chat li")).toHaveClass("active");
    });
    it("rimuovi3", function() {
        var chat = new PT.VIEW.Chat({
            canale: 'Canale',
            destinatari: 'destinatari',
            tipo: 'data'
        });
        var chat2 = new PT.VIEW.Chat({
            canale: 'Canale2',
            destinatari: 'destinatari2',
            tipo: 'data'
        });
        $("#chat li")[0].addClass("active");
        chat.rimuovi();
        expect($("#chat li")).toHaveClass("active");


    });
    it("rimuovi4", function() {
        $("#vchat").hide();
        var chat = new PT.VIEW.Chat({
            canale: 'Canale',
            destinatari: 'destinatari',
            tipo: 'data'
        });
        chat.rimuovi();
        expect($("#tabchat")).toBeHidden();
        expect($("#logo")).toBeVisible();
    });
    it("invia", function() {
        var chat = new PT.VIEW.Chat({
            canale: 'Canale',
            destinatari: 'destinatari',
            tipo: 'data'
        });
        $("#chat a[href='#destinatari" + data.getTime() + "']").trigger("click");
        $("#tabchat form input").val("ciao");
        $("#tabchat form button").click();
        expect($("#tabchat form input").val()).toBe("");
    });
    it("rimuoviUtente", function() {
        var chat = new PT.VIEW.Chat({
            canale: 'Canale',
            destinatari: new Array('dest1', 'dest2'),
            tipo: 'data'
        });
        chat.eliminaUtente("dest2");
        expect(chat.nomi).toEqual(['dest1']);

    });
    it("hideSpan", function() {
        var chat = new PT.VIEW.Chat({
            canale: 'Canale',
            destinatari: 'dest',
            tipo: 'data'
        });
        $("#chat a").trigger("click");
        expect($("#chat a .label")).toBeHidden();

    });
});
