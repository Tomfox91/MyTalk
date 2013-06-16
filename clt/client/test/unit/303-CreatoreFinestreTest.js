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
it("creaAVChat",function(){
	PT.VIEW.AVChat=new Class({
			tipo:null,
			initialize: function(o){this.tipo=o.tipo;}
				});
	var chat=PT.VIEW.getCreatore().creaFinestra({
                tipo: "video"
            });

expect(chat.tipo).toBe("video");
});
it("creaChat",function(){
	PT.VIEW.Chat=new Class({
			tipo:null,
			initialize: function(o){this.tipo=o.tipo;}
				});

	var chat=PT.VIEW.getCreatore().creaFinestra({
                    tipo: 'data'});;
expect(chat.tipo).toBe('data');

});

it("statico",function(){
	PT.VIEW.getCreatore();
	expect(PT.VIEW.getCreatore()).not.toBeNull()	
});

});
