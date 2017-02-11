import mongoose = require("mongoose");

interface ISauce {
    label: string;
    imgURL: string;
}

var SauceSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});

interface ISauceModel extends ISauce, mongoose.Document { }

var Sauce = mongoose.model<ISauceModel>("Sauce", SauceSchema);

export = Sauce;