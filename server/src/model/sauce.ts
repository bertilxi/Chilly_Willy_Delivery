import mongoose = require('mongoose');

export interface ISauce {
    label: string;
    imgURL: string;
}

export var SauceSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});

export interface ISauceModel extends ISauce, mongoose.Document {
}

export var Sauce = mongoose.model<ISauceModel>('Sauce', SauceSchema);
