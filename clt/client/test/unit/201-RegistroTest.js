describe("Registro", function() {
    var orig_PT = PT;
    var view;

    beforeEach(function() {
        PT = Object.clone(orig_PT);
    });
    afterEach(function() {
        PT = orig_PT;
    });

    it("constructor test Registro", function() {
        var registro = PT.MODEL.getInstanceRegistro();
        expect("object").toEqual(typeOf(registro.listaUtenti)); 
        expect("object").toEqual(typeOf(registro.listaPendenti));
                
    });
    it("test Add, Rm, Setter e Modifica methods", function() {
        
        ObserverFinto = new Class ({
        
            pendenteAdd : null,
            utenteAdd : null,
            pendenteRm : null,
            utenteRm : null,
            utenteModificato : null,
            
            
            ottieniAddPendente : function(utente) {
            this.pendenteAdd = utente;
            },
            
            ottieniAddUtente : function(utente) {
            this.utenteAdd = utente;
            },
            
            
            ottieniRmPendente : function(utente) {
            this.pendenteRm = utente;
            },
            
            
            ottieniRmUtente : function(utente) {
            this.utenteRm = utente;
            },
            
            
            ottieniModificheUtente : function(utente) {
            this.utenteModificato = utente;
            }
        });

        var obs = new ObserverFinto(); // finto observer
        var registroInstance = PT.MODEL.getInstanceRegistro();
              
        
        registroInstance.removeEvents('aggUtenteModel');
        registroInstance.addEvent('aggUtenteModel', function(ciccia) {obs.ottieniAddUtente(ciccia)});
        registroInstance.removeEvents('aggPendenteModel');
        registroInstance.addEvent('aggPendenteModel', function(ciccia) {obs.ottieniAddPendente(ciccia)});
        registroInstance.removeEvents('rmUtenteModel');
        registroInstance.addEvent('rmUtenteModel', function(ciccia) {obs.ottieniRmUtente(ciccia)});
        registroInstance.removeEvents('rmPendenteModel');
        registroInstance.addEvent('rmPendenteModel', function(ciccia) {obs.ottieniRmPendente(ciccia)});
        registroInstance.removeEvents('modificaNomeUtenteModel');
        registroInstance.addEvent('modificaNomeUtenteModel', function(ciccia) {obs.ottieniModificheUtente(ciccia)});
        registroInstance.removeEvents('modificaStatoUtenteModel');
        registroInstance.addEvent('modificaStatoUtenteModel', function(ciccia) {obs.ottieniModificheUtente(ciccia)});
        
        
        var utente = new PT.MODEL.Utente("prova@me.com", "Online", "Soketto");
        var utente2 = new PT.MODEL.Utente("prova@me.com2", "ndf", "Soketto2");
        var utente3 = new PT.MODEL.Utente("mio@me.com", "Occupato", "Virto");

        registroInstance.addUtente(utente);
        expect(utente).toEqual(obs.utenteAdd);
        expect(utente).toEqual(registroInstance.listaUtenti[utente.email]);
        
        registroInstance.addPendente(utente2);
        expect(utente2).toEqual(registroInstance.listaPendenti[utente2.email]);
        expect(utente2).toEqual(obs.pendenteAdd);
        
        registroInstance.modificaStatoUtente("sleep","prova@me.com");
        expect("sleep").toEqual(obs.utenteModificato.stato);
        expect("sleep").toEqual(registroInstance.listaUtenti["prova@me.com"].stato);
        
        registroInstance.modificaNomeUtente("sokkkkkettoi","prova@me.com");
        expect("sokkkkkettoi").toEqual(obs.utenteModificato.nome);
        expect("sokkkkkettoi").toEqual(registroInstance.listaUtenti["prova@me.com"].nome);
        
        registroInstance.rmUtente("prova@me.com");
        expect(obs.utenteRm).toEqual(utente);
        expect("null").toEqual(typeOf(registroInstance.listaUtenti["prova@me.com"]));
        
        registroInstance.rmPendente("prova@me.com2");
        expect(obs.pendenteRm).toEqual(utente2);
        expect("null").toEqual(typeOf(registroInstance.listaPendenti["prova@me.com2"]));
    
        registroInstance.setUtente("email@me.com","OK","BOH");
        expect("email@me.com").toEqual(obs.utenteAdd.email);
        expect("OK").toEqual(obs.utenteAdd.stato);
        expect("BOH").toEqual(obs.utenteAdd.nome);
        
        
        expect("email@me.com").toEqual(registroInstance.listaUtenti["email@me.com"].email);
        expect("OK").toEqual(registroInstance.listaUtenti["email@me.com"].stato);
        expect("BOH").toEqual(registroInstance.listaUtenti["email@me.com"].nome);
        
        
           
    });

    
    it("test proprio utente, proprio IP, proprio id", function() {
        var registroInstance = PT.MODEL.getInstanceRegistro();
        registroInstance.setProprioUtente(undefined);
        registroInstance.setProprioIP("111.111.0.1");
        expect(registroInstance.getProprioId()).toEqual("111.111.0.1");
        expect(registroInstance.getProprioNome()).toEqual("111.111.0.1");
        expect("111.111.0.1").toEqual(registroInstance.getProprioNome());

        var utente = new PT.MODEL.Utente("mio@me.com", "Occupato", "Virto");
        registroInstance.setProprioUtente(utente);

        expect(registroInstance.getProprioId()).toEqual("mio@me.com");
        expect(registroInstance.getProprioNome()).toEqual("Virto");

        var x = registroInstance.getProprioUtente();
        expect("mio@me.com").toEqual(registroInstance.proprioUtente.getEmail());
        expect("Occupato").toEqual(registroInstance.proprioUtente.getStato());
        expect("Virto").toEqual(registroInstance.proprioUtente.getNome());
        expect("Virto").toEqual(registroInstance.getProprioNome());
        
        
        
    });
    
    
    it("test Singleton", function() {
        var registroInstance1 = PT.MODEL.getInstanceRegistro();
        var utente = new PT.MODEL.Utente("prova@me.com", "Online", "Soketto");
        registroInstance1.addUtente(utente);
        var registroInstance2 = PT.MODEL.getInstanceRegistro();
        expect(registroInstance1).toEqual(registroInstance2);
    });
    
    it("test getter", function() {
        var registroInstance = PT.MODEL.getInstanceRegistro();
        var utente = new PT.MODEL.Utente("prova@me.com", "Online", "Soketto");
        var utente2 = new PT.MODEL.Utente("prova@me.com2", "Online2", "Soketto2");
        var utente3 = new PT.MODEL.Utente("prova@me.com3", "Online3", "Soketto3");
        var utente4 = new PT.MODEL.Utente("prova@me.com4", "Online4", "Soketto4");
        var utente5 = new PT.MODEL.Utente("prova@me.com5", "Online5", "Soketto5");
        var utente6 = new PT.MODEL.Utente("prova@me.com6", "Online6", "Soketto6");
        
        registroInstance.addUtente(utente);
        registroInstance.addUtente(utente2);
        registroInstance.addUtente(utente3);
        registroInstance.addUtente(utente4);
        
        var lista = registroInstance.getListaUtenti();
        expect(utente).toEqual(lista["prova@me.com"]);
        expect(utente4).toEqual(lista["prova@me.com4"]);
        
        registroInstance.addPendente(utente5);
        registroInstance.addPendente(utente6);
        var lista2 = registroInstance.getListaPendenti();
        expect(utente5).toEqual(lista2["prova@me.com5"]);
        expect(utente6).toEqual(lista2["prova@me.com6"]);
        
        
    }); 
    
});
