import qupath.tensorflow.stardist.StarDist2D

// Specify the model directory (you will need to change this!)
def pathModel = 'C:/Users/Mark Zaidi/Documents/QuPath/Stardist Trained Models/dsb2018_heavy_augment'

def stardist = StarDist2D.builder(pathModel)
        .threshold(0.5)              // Probability (detection) threshold
        .channels('DAPI')            // Specify detection channel
        .normalizePercentiles(1, 99) // Percentile normalization
        .pixelSize(0.5)              // Resolution for detection
        .build()

// Run detection for the selected objects
def imageData = getCurrentImageData()
def pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}
stardist.detectObjects(imageData, pathObjects)
println 'Done!'