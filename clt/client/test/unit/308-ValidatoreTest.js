describe("Validatore", function() {
    
    var orig_PT = PT;
    
    beforeEach(function() {
       PT = Object.clone(orig_PT);
       jasmine.getFixtures().fixturesPath = '/test/public_html/html';
       jasmine.getFixtures().load('MyTalk-fixture.html');
       PT.VIEW.getInstanceSubjectView = function() {view = jasmine.createSpyObj("view",["fireEvent"]);return view};
       
    });
    
    afterEach(function() {
        PT = orig_PT;
    });
    
    it("validaIP", function() {
       var validator = new PT.VIEW.ValidatoreInput();
       var setIPeRun = function (IP) {
                            $("#chiamaIP").hide();
                            $("#ip").val(IP);
                            validator.validaIP();
                            };
       var asserts = function(corretto) {
           if (corretto) {
               expect($('#cercaIP span')).toBeHidden();
               expect($('#chiamaIP')).toBeVisible();    
           }
           
           else if (!corretto) {
               expect($('#cercaIP span')).toBeVisible();
               expect($('#chiamaIP')).toBeHidden();
           
           }
       };
       
        //IP SBAGLIATI
        
       setIPeRun("cicciabubu");
       asserts(false);
       
       setIPeRun("183.0.1.");
       asserts(false);
       
       setIPeRun("183.0.1..");
       asserts(false);
       
       setIPeRun("256.234.123.1");
       asserts(false);
       
       setIPeRun("0.256.123.1");
       asserts(false);
       
       setIPeRun("0.0.256.1");
       asserts(false);
       
       setIPeRun("0.0.0.256");
       asserts(false);
       
       setIPeRun("2552552552551");
       asserts(false);
       
       setIPeRun("-1");
       asserts(false);  
       //setIPeRun("265255255255");
       //expect($('#cercaIP span')).toBeVisible();
       
       //IP GIUSTI
       setIPeRun("192.183.0.1");
       asserts(true);
       
       setIPeRun("0.0.0.0");
       asserts(true);
       
       setIPeRun("255.255.255.255");
       asserts(true);
       
       setIPeRun("192.183.0.1");
       asserts(true);
       
       setIPeRun("192.183.0.1");
       asserts(true);

       setIPeRun("183.0.1");
       asserts(false);
       
       setIPeRun("149.149.149.149");
       asserts(true);
       
       setIPeRun("19216801");
       asserts(false);
       
       setIPeRun("255255255255");
       asserts(false);


    });
    
    it("registra ok + 1 errore", function() {
    var setUp = function () {
        $("#user").parent("div").find("span").hide();
        $("#pswc").parent("div").find("span").hide();
    }
    var validator = new PT.VIEW.ValidatoreInput();
       $("#user").val("Michele");
       $("#emailR").val("mdurello@gmail.com");
       $("#psw").val("Pisolo");
       $("#pswc").val("Pisolo");
       
       setUp();
       validator.registra();
              
       $("#user").val("");
       $("#emailR").val("@gmail.com");
       $("#pswc").val("pisolo");
       
       setUp();
       validator.registra();
       
       expect($("#user").parent("div").parent("div")).toHaveClass("error");
       expect($("#emailR").parent("div").parent("div")).toHaveClass("error");
       expect($("#psw").parent("div").parent("div")).toHaveClass("error");
       expect($("#pswc").parent("div").parent("div")).toHaveClass("error");
       
       $("#psw").parent("div").parent("div").removeClass("error");
       $("#pswc").parent("div").parent("div").removeClass("error");
       $("#pswc").parent("div").find("span").hide();
       $("#emailR").parent("div").find("span").hide();
       $("#emailR").parent("div").parent("div").removeClass("error");
       $("#user").parent("div").parent("div").removeClass("error");
       $("#user").parent("div").find("span").hide();
       
       $("#user").val("22Cani");
       $("#emailR").val("ciaogmail.com");
       
       setUp();
       validator.registra();
       
       expect($("#user").parent("div").parent("div")).toHaveClass("error");
       expect($("#emailR").parent("div").parent("div")).toHaveClass("error");
       
       $("#emailR").parent("div").find("span").hide();
       $("#emailR").parent("div").parent("div").removeClass("error");
       $("#user").parent("div").parent("div").removeClass("error");
       $("#user").parent("div").find("span").hide();
        
       $("#user").val("Cani22"); 
       $("#emailR").val("ciao@gmailcom");
       
       setUp();
       validator.registra();
       
       expect($("#user").parent("div").parent("div")).not.toHaveClass("error");
       expect($("#emailR").parent("div").parent("div")).toHaveClass("error");
       
       $("#emailR").parent("div").find("span").hide();
       $("#emailR").parent("div").parent("div").removeClass("error");
       $("#user").parent("div").parent("div").removeClass("error");
       $("#user").parent("div").find("span").hide();
        
       $("#user").val("Canì22");
       $("#emailR").val("ciao_91@gmail.com");
       
       setUp();
       validator.registra();
       
       expect($("#user").parent("div").parent("div")).not.toHaveClass("error");
       expect($("#emailR").parent("div").parent("div")).not.toHaveClass("error");
     
       
       $("#user").val("Cani22@");
       $("#emailR").val("Cani22@mail.it");

       setUp();
       validator.registra();
       expect($("#user").parent("div").parent("div")).toHaveClass("error"); // adesso va ma dopo bisognerà metterla a posto
       expect($("#emailR").parent("div").parent("div")).not.toHaveClass("error");


       $("#user").val(" Cani22");
       
       setUp();
       validator.registra();
       
       expect($("#user").parent("div").parent("div")).toHaveClass("error");
       
       $("#user").parent("div").parent("div").removeClass("error");
       $("#user").parent("div").find("span").hide();
        
       $("#user").val("    ");
       
       setUp();
       validator.registra();
       
       expect($("#user").parent("div").parent("div")).toHaveClass("error");

    });
    
    
    it("newUsername", function() {
        var validator = new PT.VIEW.ValidatoreInput();
        $("#inputNewUsr").val(" Bislacco");
        validator.newUsername();
        
        //expect($("#inputNewUsr").parent("div").find("span")).toBeVisible(); NON VA
        expect($("#inputNewUsr").parent("div").parent("div")).toHaveClass("error");
        
        $("#inputNewUsr").parent("div").parent("div").removeClass("error");
        
        $("#inputNewUsr").val("Bislacchino");
        validator.newUsername();
        expect($("#inputNewUsr").parent("div").parent("div")).not.toHaveClass("error");
        
        
    
    });
    
    it("newPsw", function() {
        var validator = new PT.VIEW.ValidatoreInput();
        $("#pwdn").val("pistyo");
        $("#pwdnc").val("pistyo");
        
        validator.newPsw();
        
        expect(view.fireEvent).toHaveBeenCalled();
        
        $("#pwdnc").val("pisto");
        validator.newPsw();
        
        expect($("#pwdn").parent("div")).toHaveClass("error");
        $("#pwdn").parent("div").removeClass("error");

    });

    it("confirmEmail", function() {
        var validator = new PT.VIEW.ValidatoreInput();
        $("#lstem").val("PROVA_1@mail.com");
        
        $("#insem span").hide();
        validator.confirmEmail();
        
        expect($("#insem span")).toBeHidden();
        expect($("#insem")).not.toHaveClass("error");
        
        
        $("#lstem").val("@mail.com");       
        
        validator.confirmEmail();
        
        expect($("#insem span")).toBeVisible();
        expect($("#insem")).toHaveClass("error");
        
       
    });
    
    it("subNewPsw", function() {
        var validator = new PT.VIEW.ValidatoreInput();
        $("#inputNewPsw").val("pista");
        $("#inputConfPsw").val("Pista");
        $("#inputConfPsw").parent("div").find("span").hide();
        
        validator.subNewPsw();
        
        expect($("#inputNewPsw").parent("div").parent("div")).toHaveClass("error");
        expect($("#inputConfPsw").parent("div").parent("div")).toHaveClass("error");
        expect($("#inputConfPsw").parent("div").find("span")).toBeVisible();
        
        $("#inputNewPsw").parent("div").parent("div").removeClass("error");
        $("#inputConfPsw").parent("div").parent("div").removeClass("error");
        $("#inputConfPsw").parent("div").find("span").hide();
              
        $("#inputConfPsw").val("pista");
        validator.subNewPsw();
        
        expect($("#inputNewPsw").parent("div").parent("div")).not.toHaveClass("error");
        expect($("#inputConfPsw").parent("div").parent("div")).not.toHaveClass("error");
        expect($("#inputConfPsw").parent("div").find("span")).toBeHidden();
        
    });

});
