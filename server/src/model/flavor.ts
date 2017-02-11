import mongoose = require("mongoose");

interface IFlavor {
	/** should it have an identifier attribute? */
    label: string;
    imgURL: string;
}

var FlavorSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});

interface IFlavorModel extends IFlavor, mongoose.Document { }

var Flavor = mongoose.model<IFlavorModel>("Flavor", FlavorSchema);

export = Flavor;