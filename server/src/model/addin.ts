import mongoose = require("mongoose");

export interface IAddin {
    label: string;
    imgURL: string;
}

export var AddinSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});

export interface IAddinModel extends IAddin, mongoose.Document { }

export var Addin = mongoose.model<IAddinModel>("Addin", AddinSchema);
