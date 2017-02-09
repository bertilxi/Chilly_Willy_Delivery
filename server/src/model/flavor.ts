import mongoose = require("mongoose");

interface IFlavor {
    label: string;
    img: string;
}

var FlavorSchema = new mongoose.Schema({
    label: String,
    img: String
});

interface IFlavorModel extends IFlavor, mongoose.Document { }

var Flavor = mongoose.model<IFlavorModel>("Flavor", FlavorSchema);

export = Flavor;