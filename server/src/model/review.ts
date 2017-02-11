import mongoose = require("mongoose");


export interface IReview {
    rating: number;
    img: any;
    comment: string;
}

export var ReviewSchema = new mongoose.Schema({
    rating: Number,
    imgUrl: String,
    comment: String,
});

export interface IReviewModel extends IReview, mongoose.Document { }

export var Review = mongoose.model<IReviewModel>("Review", ReviewSchema);


