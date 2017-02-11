import { IContainerType } from '../model/container-type';

export var containerTypes: Array<IContainerType> = [];

containerTypes.push({
    label: "Pote - 1/4",
    maxFlavors: 2,
    variableQuantityOfFlavors: true,
});

containerTypes.push({
    label: "Pote - 1/2",
    maxFlavors: 3,
    variableQuantityOfFlavors: true,
});

containerTypes.push({
    label: "Pote - 3/4",
    maxFlavors: 4,
    variableQuantityOfFlavors: true,
});

containerTypes.push({
    label: "Pote - Kilo",
    maxFlavors: 4,
    variableQuantityOfFlavors: true,
});