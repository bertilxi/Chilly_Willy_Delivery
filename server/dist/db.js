"use strict";
const Flavor = require("./model/flavor");
const ContainerSize = require("./model/container-size");
const ContainerType = require("./model/container-type");
const Sauce = require("./model/sauce");
const Addin = require("./model/addin");
const flavors_1 = require("./data/flavors");
const container_size_1 = require("./data/container-size");
const container_types_1 = require("./data/container-types");
const sauce_1 = require("./data/sauce");
const addins_1 = require("./data/addins");
class DbHelper {
    constructor() {
        this.resetMetadata();
    }
    resetMetadata() {
        Flavor.remove({}, () => { });
        ContainerSize.remove({}, () => { });
        ContainerType.remove({}, () => { });
        Sauce.remove({}, () => { });
        Addin.remove({}, () => { });
    }
    init() {
        flavors_1.flavors.forEach(x => { new Flavor(x).save(); });
        container_size_1.containerSizes.forEach(x => { new ContainerSize(x).save(); });
        container_types_1.containerTypes.forEach(x => { new ContainerType(x).save(); });
        sauce_1.sauces.forEach(x => { new Sauce(x).save(); });
        addins_1.addins.forEach(x => { new Addin(x).save(); });
    }
}
exports.DbHelper = DbHelper;
