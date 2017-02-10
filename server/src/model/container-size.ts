import mongoose = require("mongoose");

interface IContainerSize {
    label: string;
}

var ContainerSizeSchema = new mongoose.Schema({
    label: String
});

interface IContainerSizeModel extends IContainerSize, mongoose.Document { }

var ContainerSize = mongoose.model<IContainerSizeModel>("ContainerSize", ContainerSizeSchema);

export = ContainerSize;