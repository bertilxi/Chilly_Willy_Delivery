import mongoose = require("mongoose");


interface IReview {
    rating: number;
    img: any;
    comment: string;
}

var ReviewSchema = new mongoose.Schema({
    rating: Number,
    imgUrl: String,
    comment: String,
});

interface IReviewModel extends IReview, mongoose.Document { }

var Review = mongoose.model<IReviewModel>("Review", ReviewSchema);

export = Review;



