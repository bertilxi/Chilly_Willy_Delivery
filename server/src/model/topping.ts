import mongoose = require('mongoose');

export interface ITopping {
    label: string;
    imgURL: string;
}

export var ToppingSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});

export interface IToppingModel extends ITopping, mongoose.Document {
}

export var Topping = mongoose.model<IToppingModel>('Topping', ToppingSchema);
