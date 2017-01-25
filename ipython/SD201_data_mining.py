import sklearn as sk
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.neighbors import KNeighborsClassifier

# totla of files: 20
fileList=["8 Health Benefits of Apples",
          "An Apple a Day to Keep 5 Chronic Diseases Away?",
          "Apple adds SIM-free iPhone 7 and 7 Plus option in the US",
          "Apple: From iPhones to iCars",
          "Apple hires a Carnegie Mellon professor to improve its AI",
          "Apple in India: Forbidden fruit",
          "Apple may ditch traditional USB ports on the MacBook Pro",
           "Apple reportedly wants to use changeable E Ink keyboards",#8
           "Apples: Health Benefits, Facts, Research",
           "Apples Ranked Second Highest for Antioxidant Activity",
           "Apple suspends developer account over 'review fraud'",
           "Apple’s winning streak is soon to end",
           "Apple Watch Nike+ arrives on October 28th",#5
           "Are apple seeds poisonous?",
           "Bloomberg: Apple isn't building a car anymore",
           "Corporate taxation Bruised Apple",
           "Health benefits of apple",
           "Smartphones Still ringing bells",
           "Some People May Need to Eat Apples in Moderation",
           "What's New and Beneficial About Apples"]
trainingRC=['apple:fruits',
          'apple:fruits',
          'apple:company',
          'apple:company',
          'apple:company',
          'apple:company',
          'apple:company',
          'apple:company',#8
          'apple:fruits',
          'apple:fruits',
          'apple:company',
          'apple:company',
          'apple:company',#5
          'apple:fruits',
          'apple:company',
          'apple:company',
          'apple:fruits',
          'apple:company',
          'apple:fruits',
          'apple:fruits']
trainingCl=[]
testingCl=[]
trainingFiles=[]
testingFiles=[]

def readSets(files):
    filesIN=[]
    for i in files:
        readfile= open(i,'r')
        filesIN.append(readfile.read())
        readfile.close()
    return filesIN

def randomFiles(files, count,trainingRC):
    trainingCl=[]
    testingCl=[]
    trainingFiles=[]
    testingFiles=[]
    j=0
    for i in range(0,count): 
        j=0
        while(j in trainingFiles):
            j=random.randrange(0, 20)   
        trainingFiles.append(files[j])
        trainingCl.append(trainingRC[j])
                          
    for i in range(0,len(files)):
        if j not in trainingFiles:
            testingFiles.append(files[i])
            testingCl.append(trainingRC[i]) 
    return trainingFiles,testingFiles,trainingCl,testingCl

appleFiles= readSets(fileList)
resulstA=[]
resulstK=[]
maxres=0
kmax=0
for i in range(1,14): 
    trainingFiles,testingFiles,trainingCl,testingCl= randomFiles(appleFiles, 14,traingRC)       
    count_vect = CountVectorizer()
    X_train_counts = count_vect.fit_transform(trainingFiles)
    training=X_train_counts.toarray()
    neigh = KNeighborsClassifier(n_neighbors=i)
    neigh.fit(training,trainingCl)

    X_testing_counts = count_vect.transform(testingFiles)
    testing=X_testing_counts.toarray()

    results=neigh.score(testing,testingCl)
    resulstA.append(results)
    resulstK.append(i)
    if results>maxres: 
        maxres=results
        kmax=i
print "maxres:",maxres
print "kmax:",kmax
print 'k:', resulstK
print 'Accuracy:',resulstA
