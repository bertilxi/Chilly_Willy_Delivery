import mongoose = require("mongoose");

interface IAddin {
    label: string;
    imgURL: string;
}

var AddinSchema = new mongoose.Schema({
    label: String,
    imgURL: String
});

interface IAddinModel extends IAddin, mongoose.Document { }

var Addin = mongoose.model<IAddinModel>("Addin", AddinSchema);

export = Addin;