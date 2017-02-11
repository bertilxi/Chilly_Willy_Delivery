import mongoose = require("mongoose");

interface IContainerType {
    label: string;
    maxFlavors: number;
    variableQuantityOfFlavors: boolean;
}

var ContainerTypeSchema = new mongoose.Schema({
    label: String,
    maxFlavors: Number,
    variableQuantityOfFlavors: Boolean
});

interface IContainerTypeModel extends IContainerType, mongoose.Document { }

var ContainerType = mongoose.model<IContainerTypeModel>("ContainerType", ContainerTypeSchema);

export = ContainerType;