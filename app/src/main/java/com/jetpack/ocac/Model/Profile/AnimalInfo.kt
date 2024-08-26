package com.jetpack.ocac.Model.Profile

data class AnimalInfo(
    val animalplotdetails: List<Animalplotdetail>,
    val largeAnimal: List<LargeAnimal>,
    val largeAnimalStatus: String,
    val poultryAnimal: List<PoultryAnimal>,
    val poultryAnimalStatus: String,
    val smallAnimal: List<SmallAnimal>,
    val smallAnimalStatus: String
)