import mongoose = require("mongoose");

interface IContainerType {
    label: string;
}

var ContainerTypeSchema = new mongoose.Schema({
    label: String
});

interface IContainerTypeModel extends IContainerType, mongoose.Document { }

var ContainerType = mongoose.model<IContainerTypeModel>("ContainerType", ContainerTypeSchema);

export = ContainerType;