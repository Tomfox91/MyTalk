describe("GestoreNuovaChiamata", function() {

   
    it("test", function() {
    //INIZIO PREPARAZIONE
    
        orig_canaleT = PT.CONTROLLER.S.CanaleT;
        orig_canaleAV = PT.CONTROLLER.S.CanaleAV;
        
        var canTx = new Class ({
        
            richiediAggiuntaDestinatario : function(value) {}
        
        });
        
        var canAVx = new Class ({
        
            richiediAggiuntaDestinatario : function(value) {}
        
        });
        
        var canT = new canTx();
        var canAV = new canTx();
        
        PT.CONTROLLER.S.CanaleT = function() {
            return canT;
        }
        
        PT.CONTROLLER.S.CanaleAV = function() {
            return canAV;
        }
        
        var orig_$ = window.$;
        
        var boh = new Class ({
            val : function () {
            return 'MultiChat';
            }
        });
        
        var bohI = new boh();
        
        window.$ = function(str) {
            if (str = '[name="unomolti"]:checked');
            return bohI;
        };

        
    //FINE PREPARAZIONE
    spyOn(canT, 'richiediAggiuntaDestinatario');
    ar = new Array("prova1","prova2", "prova_n", "fine_prova");
    
    new PT.CONTROLLER.G.GestoreNuovaChiamata().esegui({tipo : "data",
                                                       destinatario : ar
                                                      });
    expect(canT.richiediAggiuntaDestinatario.calls.length).toEqual(4);
    
    spyOn(canAV, 'richiediAggiuntaDestinatario');
    ar = new Array("prova1","prova2");
    new PT.CONTROLLER.G.GestoreNuovaChiamata().esegui({tipo : "audio-video",
                                                       destinatario : ar
                                                      });
    expect(canAV.richiediAggiuntaDestinatario.calls.length).toEqual(2);
    //PREPARAZIONE INTERMEDIA
    
    var boh = new Class ({
            val : function () {
            return 'MultiChatTT';
            }
        });
        
    var bohI = new boh();
    
    
    
    //FINE PREPARAZIONE INTERMEDIA
    
    new PT.CONTROLLER.G.GestoreNuovaChiamata().esegui({tipo : "data",
                                                       destinatario : "ciao@me.com"
                                                      });
    
    expect(canT.richiediAggiuntaDestinatario).toHaveBeenCalledWith("ciao@me.com");
     
    new PT.CONTROLLER.G.GestoreNuovaChiamata().esegui({tipo : "audio-video",
                                                       destinatario : "PROVA1@me.com"
                                                      });
    expect(canAV.richiediAggiuntaDestinatario).toHaveBeenCalledWith("PROVA1@me.com");
    //INIZIO PULIZIA
    
        PT.CONTROLLER.S.CanaleT = orig_canaleT;
        PT.CONTROLLER.S.CanaleAV = orig_canaleAV;
        window.$ = orig_$;
        delete orig_$;
        delete canT;
        delete canTx;
        delete canAVx;
        delete canAV;
        delete boh;
        delete bohI;

    });
        
});















  
