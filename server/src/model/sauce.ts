import mongoose = require("mongoose");

interface ISauce {
    label: string;
}

var SauceSchema = new mongoose.Schema({
    label: String
});

interface ISauceModel extends ISauce, mongoose.Document { }

var Sauce = mongoose.model<ISauceModel>("Sauce", SauceSchema);

export = Sauce;