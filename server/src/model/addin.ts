import mongoose = require("mongoose");

interface IAddin {
    label: string;
}

var AddinSchema = new mongoose.Schema({
    label: String
});

interface IAddinModel extends IAddin, mongoose.Document { }

var Addin = mongoose.model<IAddinModel>("Addin", AddinSchema);

export = Addin;