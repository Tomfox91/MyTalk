describe("GestoreMessaggioSegreteria", function(){

   
    it("test", function() {
        
        var orig_dispatcher = PT.CONTROLLER.getInstanceDispatcher;
        
        var t;
        var d;
        var m;
        
        var socket = new Class ({
            
            invia : function(e) { 
                t = e.tipo;
                d = e.evento.destinatario;           
                m = e.evento.messaggio;
            }
        
        });    


        var dispatcher = new Class ({

            getSocket : function() { 
                return new socket();
            }

        });

        PT.CONTROLLER.getInstanceDispatcher = function() {
            return new dispatcher();
        };
        
        
        
        
        
        
        
        //INIZIO PREPARAZIONE
        var txt = {
          
            text : function () {return String("Destinatario: Pluto");}
            
         };
         
         var mx = {
            
            val : function () {return String("Prova messaggio di segreteria");}
            
         };
         
         var orig_$ = window.$;
         window.$ = function(str) {
         
            if (str == "#msgbody p")
                return txt;
            if (str == "#msgbody textarea")
                return mx;
        
        };
        
        //FINE PREPARAZIONE
        
        ciccia = new PT.CONTROLLER.G.GestoreMessaggioSegreteria().esegui();
        expect(t).toBe("MessaggioSegreteria");
        expect(d).toBe("Pluto");
        expect(m).toBe("Prova messaggio di segreteria");
        
        //INIZIO PULIZIA
        
        window.$ = orig_$;
        delete orig_$;
        delete txt;
        delete mx;
        delete t;
        delete d;
        delete m;
        PT.CONTROLLER.getInstanceDispatcher = orig_dispatcher;
        delete orig_dispatcher;
        delete socket;
        delete dispatcher;
        
        
    });


});




