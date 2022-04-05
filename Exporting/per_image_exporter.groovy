def name = getProjectEntry().getImageName() + '.csv'
def path = buildFilePath(PROJECT_BASE_DIR, 'cell feature results')
mkdirs(path)
path = buildFilePath(path, name)
saveDetectionMeasurements(path)
print 'Results exported to ' + path