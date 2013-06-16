describe("AVChat", function() {
    var orig_PT = PT;
    var view;
    beforeEach(function() {
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');
        PT.VIEW.getInstanceSubjectView = function() {
            view = jasmine.createSpyObj("view", ["fireEvent"]);
            return view;

        };
        var ut=new PT.MODEL.Utente("e@ma.il", "Online", "nome");
	  var ut2=new PT.MODEL.Utente("e2@ma.il", "Offline", "nome2");
	
	        PT.MODEL.getInstanceRegistro().getListaUtenti=function(){return{"e@ma.il":ut,"e2@ma.il":ut2};};
        $("#tabchat").hide();

    });
    afterEach(function() {
        PT = orig_PT;

        PT.MODEL.getInstanceRegistro().setProprioUtente(new PT.MODEL.Utente("e@ma.il", "Online", "personale"));
        $("#vchat audio").remove();
        $("#vchat video").remove();
    });
    it("crazioneLocale", function() {
        var chat = new PT.VIEW.AVChat({
            elem: "<audio></audio>",
            locale: 1,
            id: "id",
            canale: "Canale",
            tipo: 'audio'
        });
        expect(chat.tipo).toBe("audio");
        expect(chat.channel).toBe("Canale");
        expect($("#add").find(".modal-footer").find(".btn-primary")).toBeVisible();
        expect($("#myv")).toContainHtml("<audio></audio>");
        expect($("#tools")).toBeVisible();
        expect($("#tools").find("button[href='#AVCHAT']")).toBeVisible();
        $("#aggiungi").click();
        $("#btnAggiungi")[0].fireEvent("click");
    });
    it("creazioneRemoto", function() {
        var chat = new PT.VIEW.AVChat({
            elem: "elem",
            locale: 0,
            id: "id",
            canale: "Canale",
            tipo: 'video'
        });
        expect($("#vchat")).toContainText("elem");
        expect($("#myv")).toBeVisible();
        expect($("#logo")).toBeHidden();
        expect($("#vchat")).toBeVisible();
        $("#stat").popover("toggle");
        expect($("#tools .popover")).toBeVisible();
        expect($(".multy")).toBeHidden();
    });
    it("rimuoviStream", function() {

        var chat = new PT.VIEW.AVChat({
            elem: new Element("video"),
            locale: false,
            id: "id",
            canale: "Canale",
            tipo: 'video'
        });
        chat.rimuoviStream("id");

        expect($("#vchat")).not.toContainHtml('<video id="id" style="width: 80%;"></video>');

        var chat2 = new PT.VIEW.AVChat({
            elem: new Element("video"),
            locale: false,
            id: "id",
            canale: "Canale",
            tipo: 'video'
        })
        chat2.rimuoviStream("id2");

        expect($("#vchat")).toContainHtml('<video id="id" style="width: 80%;"></video>');
    });
    it("creazioneIp", function() {
        PT.MODEL.getInstanceRegistro().setProprioUtente(null);
        var chat = new PT.VIEW.AVChat({
            elem: new Element("video"),
            locale: false,
            id: "id",
            canale: "Canale",
            tipo: 'video'
        });
        expect($("#aggiungi")).toHaveAttr("disabled", "disabled");
    });
    it("setStatistiche", function() {
        var chat = new PT.VIEW.AVChat({
            elem: new Element("video"),
            locale: true,
            id: "id",
            canale: "Canale",
            tipo: 'video'
        });
        $("#stat").popover("toggle");
        chat.setStatistiche({sent: "42", rate: "42", roundTrip: "42", time: "42"});
        var a = "&agrave;";
        expect($('#tools .popover').find('.popover-content').find("li")[0]).toHaveText("Dati inviati: 42 kB");
        expect($('#tools .popover').find('.popover-content').find("li")[1]).toHaveText("Velocità attuale: 42 kBps");
        expect($('#tools .popover').find('.popover-content').find("li")[2]).toHaveText("Latenza: 42 ms");
        expect($('#tools .popover').find('.popover-content').find("li")[3]).toHaveText("Durata: 42 s");
    });
    it("destinatari", function() {
        var chat = new PT.VIEW.AVChat({
            elem: new Element("video"),
            locale: true,
            id: "id",
            canale: "Canale",
            tipo: 'video'
        });
        chat.setDestinatari("dest");
        expect(chat.destinatari).toBe("dest");
    });
    it("setSize", function() {
        var chat = new PT.VIEW.AVChat({
            elem: new Element("video"),
            locale: false,
            id: "id",
            canale: "Canale",
            tipo: 'video'
        });
        var chat2 = new PT.VIEW.AVChat({
            elem: new Element("video"),
            locale: false,
            id: "id2",
            canale: "Canale",
            tipo: 'video'
        });
        expect($("#vchat>video")).toHaveAttr("style", "width: 47%;");
    });

    it("rimuovi", function() {
        var chat = new PT.VIEW.AVChat({
            elem: new Element("video"),
            locale: true,
            id: "id",
            canale: "Canale",
            tipo: 'video'
        });

        expect($("#vchat")).toBeVisible();

        chat.rimuovi();

        expect($("#vchat")).toBeHidden();

    });


});
