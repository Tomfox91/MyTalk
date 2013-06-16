describe("Finestra", function() {
var event;

    var orig_PT = PT;
beforeEach(function() {
        PT = Object.clone(orig_PT);
        jasmine.getFixtures().fixturesPath = '/test/public_html/html';
        jasmine.getFixtures().load('MyTalk-fixture.html');
        event=jasmine.createSpyObj("event",["fireEvent"]);
        });
    afterEach(function() {
        PT = orig_PT;
    });
   it("creaBottone",function(){
          var finestra=new PT.VIEW.Finestra();
          var but=finestra.cbutton("nome",event);
          expect(but).toHaveClass("close");
          expect(but).toHaveAttr("href","#nome");
          but.click();
          expect(event.fireEvent).toHaveBeenCalled();
   
   });
});
