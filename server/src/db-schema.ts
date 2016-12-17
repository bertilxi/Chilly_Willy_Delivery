
import mongoose = require('mongoose');

export class DbSchema {
    constructor() {
        let schema = new mongoose.Schema({

        })

        mongoose.model('db-chema', schema);

    }

}
/*
exports = module.exports = function (app, mongoose) {

    var missingDogSchema = new mongoose.Schema({
        raza: {
            type: String
        },
        nombre: {
            type: String
        },
        descripcion: {
            type: String
        },
        lugar: {
            type: String
        },
        encontrado: {
            type: Boolean
        }

    });

    mongoose.model('missingDog', missingDogSchema);

};
*/