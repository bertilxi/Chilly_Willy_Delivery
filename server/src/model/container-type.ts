import mongoose = require("mongoose");

export interface IContainerType {
    label: string;
    maxFlavors: number;
    variableQuantityOfFlavors: boolean;
}

export var ContainerTypeSchema = new mongoose.Schema({
    label: String,
    maxFlavors: Number,
    variableQuantityOfFlavors: Boolean
});

export interface IContainerTypeModel extends IContainerType, mongoose.Document { }

export var ContainerType = mongoose.model<IContainerTypeModel>("ContainerType", ContainerTypeSchema);
