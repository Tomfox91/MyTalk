describe("init", function() {

    it("test", function() {
        expect(PT.CONTROLLER.G).not.toBe(null);

        var b = false;
        try {
            assert(false);
        } catch (e) {
            b = true;
        }

        expect(b).toBe(true);
    });
});
