describe("Utility", function() {
    var orig_PT = PT;
    var view;
    var validatore;
    var gestore;
    var updater;
    beforeEach(function() {
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');
        PT.MODEL.getInstanceRegistro().setProprioUtente(new PT.MODEL.Utente("io@ma.il", "Online", "io"));
        PT.VIEW.getInstanceUpdater().aggiuntoAmico(new PT.MODEL.Utente("e@ma.il", "Online", "nome"));
        PT.VIEW.getInstanceUpdater().aggiuntoAmico(new PT.MODEL.Utente("e2@ma.il", "Offline", "nome2"));
        PT.VIEW.getInstanceUpdater().aggiungiPendente(new PT.MODEL.Utente("e3@ma.il", "Offline", "nome3"));
        PT.VIEW.getInstanceUpdater().visualizzaRicerca({"e4@ma.il":"nom3"});
        PT.VIEW.getInstanceSubjectView = function() {
            view = jasmine.createSpyObj("view", ["fireEvent"]);
            return view;
        };
        PT.VIEW.ValidatoreInput = function() {
                validatore = jasmine.createSpyObj("validatore", ["validaIP", "registra", "newUsername", "subNewPsw", "confirmEmail", "newPsw"]);
                return validatore;
        };
        PT.VIEW.ValidatoreInput();
        PT.VIEW.GestoreAccount = function() {
                gestore = jasmine.createSpyObj("gestore", ["cancella", "cambioStatoPersonale"]);
                return gestore;
        };
        PT.VIEW.GestoreAccount();
        PT.VIEW.getInstanceUpdater=function(){return updater=jasmine.createSpyObj("updater",["trattaRichiestaPendente","scriviMessaggioSegreteria"]);};
        aggiungiEventi();
    });

    afterEach(function() {
        PT = orig_PT;
    });
    it("accedi", function() {
        $("#logbtn").click();
        expect(view.fireEvent).toHaveBeenCalled();
    });

    it("logout", function() {
        $("#logoutbtn").click();
        expect(view.fireEvent).toHaveBeenCalled();
    });
    it("controlloIP", function() {
        $("#cercaIP button").click();
        expect(validatore.validaIP).toHaveBeenCalled();
    });
    it("clickIP", function() {
        $("#ip").click();
        expect($("#ip").val()).toBe('');
        expect($("#chiamaIP")).toHaveClass("hide");
        expect($('#ip')).not.toHaveAttr("readonly");
    });
    it("registra", function() {
        $("#registra .btn-primary").click();
        expect(validatore.registra).toHaveBeenCalled();
    });
    it("submitNewUsername", function() {
        $("#inputNewUsr").parents("form").find("button").click();
        expect(validatore.newUsername).toHaveBeenCalled();
    });
    it("submitNewPsw", function() {
        $("#inputNewPsw").parents("form").find("button").click();
        expect(validatore.subNewPsw).toHaveBeenCalled();
    });
    it("elimina", function() {
        $("[name='elimina']").prop("checked",true);
        $("#elimina button").click();
        expect(gestore.cancella).toHaveBeenCalled();
    });
    it("confermaEmail", function() {
        $("#conferma").click();
        expect(validatore.confirmEmail).toHaveBeenCalled();
    });
    it("nuovaPsw", function() {
        $("#newpass").click();
        expect(validatore.newPsw).toHaveBeenCalled();
    });
    it("cerca", function() {
        $("#cercaAmiciInput").val("pattern");
        $("#cercaAmici button").click();
        expect(view.fireEvent).toHaveBeenCalled();
    });
    it("cambioStatoGui", function() {
        $("#on").trigger("click");
        expect(gestore.cambioStatoPersonale).toHaveBeenCalled();
        $("#off").trigger("click");
        expect(gestore.cambioStatoPersonale).toHaveBeenCalled();
    });
    it("selezioneMultipla", function() {
        $("#um2").trigger("change");
        expect($('#amiciOn span.checkbox')).toBeVisible();
        expect($('#multipla')).toBeVisible();
    });
    it("selezioneSingola", function() {
        $("#um1").trigger("change");
        expect($('#amiciOn span.checkbox')).toHaveClass("hide");
        expect($('#multipla')).toHaveClass("hide");
        expect($("#amiciOn a")).not.toHaveClass('nonlink');
    });
    it("ripulituraModali", function() {
        //modale registrazione
        $('#registra').modal("show");
        $('#registra').modal("hide");
        expect($("#registra div")).not.toHaveClass("error");
        expect($("#registra input").val()).toBe("");
        expect($("#registra span")).toHaveClass("hide");

        //modale recupero password
        $('#lost').modal("show");
        $('#lost').modal("hide");
        expect($("#lost div")).not.toHaveClass("error");
        expect($("#lost input").val()).toBe("");
        expect($("#lost span")).toHaveClass("hide");
        expect($("#dr p").text()).toBe("");
        expect($("#insem")).toBeVisible();
        expect($("#conferma")).toBeVisible();
        expect($("#dr")).toHaveClass("hide");
        expect($("#newpass")).toHaveClass("hide");

        //modale opzioni
        $('#opzioni').modal("show");
        $('#opzioni').modal("hide");
        expect($("#collapseOne input").val()).toBe("");
        expect($("#collapseTwo input").val()).toBe("");
       expect($("#opzioni div")).not.toHaveClass("error");
       
       //modale tutorial
       $("#tutorial").click();
       $("#tut").modal("toggle");
       var video = $("#tut iframe").attr("src");
       $("#tut").modal("toggle");
       expect($("#tut iframe").attr("src")).toEqual(video);


    });
    it("segnalazioneRichiestaPendente", function() {
        $('#tabamici span').show();
        $("#tabamici>li>a[href='#pend']").trigger("click");
        expect($('#tabamici span')).toHaveClass("hide");
    });
    it("iniziaChiamata", function() {
        $("#amiciOn a").clickover("toggle");
        var chi = $("#Online").find(".popover-content span")[0];
        newComunicazione(chi);
        expect(view.fireEvent).toHaveBeenCalled();
        $('[name=selettore]').attr('checked', true);
        chi = $("#multipla button")[0];
        newMComunicazione(chi);
        expect(view.fireEvent).toHaveBeenCalled();
        chi = $("#chiamaIP button")[0];
        newIPComunicazione(chi);
        expect(view.fireEvent).toHaveBeenCalled();
    });
    it("richiestaAmicizia",function(){
        $("#esiti a").clickover("toggle");
        var chi=$("#esiti").find(".popover-content").find("span");
        richiediAmicizia(chi);
        expect(view.fireEvent).toHaveBeenCalled();
    });
    it("trattaRichiesta",function(){
        $("#pend a").clickover("toggle");
        var chi=$("#pend ").find(".popover-content").find("span")[0];
       trattaRichiesta(chi,true);
        expect(updater.trattaRichiestaPendente).toHaveBeenCalled();
    });
    it("nuovoMessaggio",function(){
        $("#Offline a").clickover("toggle");
        var chi= $("#Offline").find(".popover-content span")[0];
        nuovoMessaggio(chi);
        expect(updater.scriviMessaggioSegreteria).toHaveBeenCalled();
    });
});

