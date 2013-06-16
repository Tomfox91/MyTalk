describe("integrazioneModel-View", function() {
    var orig_PT = PT;
    var ut;
    beforeEach(function() {
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');
        ut=new PT.MODEL.Utente("e@ma.il", "Online", "nome")
    });
    afterEach(function() {
        PT = orig_PT;
    });
    
   it("setProprioIP",function(){
      PT.MODEL.getInstanceRegistro().setProprioIP("1.1.1.1");
      expect($("#tuoIP")).toContainText("Il tuo IP: 1.1.1.1");
   });
   it("addUtente",function(){
       
       PT.MODEL.getInstanceRegistro().addUtente(ut);
               expect($('#Online')).toContain("a[href$='#e@ma.il']");
       
   });
   it("rmUtente",function(){
    PT.MODEL.getInstanceRegistro().addUtente(ut);
       PT.MODEL.getInstanceRegistro().rmUtente("e@ma.il");
       expect($('#amici')).not.toContain("a[href$='#e@ma.il']");
   });
   it("addPendente",function(){
       PT.MODEL.getInstanceRegistro().addPendente(new PT.MODEL.Utente("e@ma.il", "Online", "nome"));
               expect($('#pend')).toContain("a[href$='#e@ma.il']");
   });
   it("rmPendente",function(){
       PT.MODEL.getInstanceRegistro().addPendente(new PT.MODEL.Utente("e@ma.il", "Online", "nome"));
       PT.MODEL.getInstanceRegistro().rmPendente("e@ma.il");
               expect($('#pend')).not.toContain("a[href$='#e@ma.il']");
   });
   it("modificaNomeUtenteModel",function(){
   PT.MODEL.getInstanceRegistro().addUtente(new PT.MODEL.Utente("e@ma.il", "Online", "nome"));
       
       PT.MODEL.getInstanceRegistro().modificaNomeUtente("nuovonome","e@ma.il");
        expect($('#amici ul>li>a[href="#e@ma.il"]')).toContainText("nuovonome");
   
   });
   it("modificaStatoUtenteModel",function(){
       PT.MODEL.getInstanceRegistro().addUtente(new PT.MODEL.Utente("e@ma.il", "Online", "nome"));
       
       PT.MODEL.getInstanceRegistro().modificaStatoUtente("Occupato","e@ma.il");
               expect($("#Occupato")).toContain("a[href$='#e@ma.il']");
   });
});
