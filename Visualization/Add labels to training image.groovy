def server = getCurrentServer()
def manager = server.getManager()
def annotations = []
for (def region in manager.getRegions()) {
    def roi = ROIs.createRectangleROI(region)
    def server2 = manager.getServer(region, 1)
    def name = server2.getMetadata().getName()
    //Additional string operations to shorten the name
    name=name.split("\\.")[0].split('___')[1]
    //Example above: split name at a dot, then take the first substring. 
    //Then, split this at a triple underscore, and take the second substring.

    def pathObject = PathObjects.createAnnotationObject(roi)
    pathObject.setName(name)
    annotations << pathObject
}
addObjects(annotations)