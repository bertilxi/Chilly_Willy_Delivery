import mongoose = require('mongoose');

export interface IFlavor {
    /** should it have an identifier attribute? */
    label: string;
    imgURL: string;
}

export var FlavorSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});

export interface IFlavorModel extends IFlavor, mongoose.Document {
}

export var Flavor = mongoose.model<IFlavorModel>('Flavor', FlavorSchema);