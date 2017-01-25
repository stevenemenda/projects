
# coding: utf-8

# In[30]:

import sklearn as sk
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.neighbors import KNeighborsClassifier
from pathlib import Path
import sys
import random
from os import listdir
from os.path import isfile, join

# totla of files: 20
fileList=["8 Health Benefits of Apples",
#           "An Apple a Day to Keep 5 Chronic Diseases Away?", #?
          "Apple adds SIM-free iPhone 7 and 7 Plus option in the US",
#           "Apple: From iPhones to iCars", #:
          "Apple hires a Carnegie Mellon professor to improve its AI",
#           "Apple in India: Forbidden fruit",#:
          "Apple may ditch traditional USB ports on the MacBook Pro",
           "Apple reportedly wants to use changeable E Ink keyboards",#8
#            "Apples: Health Benefits, Facts, Research",#:
           "Apples Ranked Second Highest for Antioxidant Activity",
           "Apple suspends developer account over 'review fraud'",
           "Apples winning streak is soon to end",
           "Apple Watch Nike+ arrives on October 28th",#5
#             "Are apple seeds poisonous?", #?
#             "Bloomberg: Apple isn't building a car anymore",#:
           "Corporate taxation Bruised Apple",
           "Health benefits of apple",
           "Smartphones Still ringing bells",
           "Some People May Need to Eat Apples in Moderation",
            "What's New and Beneficial About Apples"]
trainingRCS=['apple:fruits',
#           'apple:fruits',
          'apple:company',
#           'apple:company',
          'apple:company',
#           'apple:company',
          'apple:company',
          'apple:company',#8
#           'apple:fruits',
          'apple:fruits',
          'apple:company',
          'apple:company',
          'apple:company',#5
#           'apple:fruits',
#           'apple:company',
          'apple:company',
          'apple:fruits',
          'apple:company',
          'apple:fruits',
          'apple:fruits']
def readSets(path):
    mypath='E:\\SD201-data-mining\\apple\\'
    myfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]
    for i in myfiles
        readfile= open(i,'r')
        trainingRC.append(trainingRCS[files.index(i)])
        filesIN.append(readfile.read())
        readfile.close()
    return filesIN,trainingRC

# def readSets(files):
#     filesIN=[]
#     trainingRC=[]
#     for i in files:
#         my_file = Path('E:\\SD201-data-mining\\apple\\'+ i)
#         if my_file.is_file():
#             readfile= open('E:\\SD201-data-mining\\apple\\','r')
#             trainingRC.append(trainingRCS[files.index(i)])
#             filesIN.append(readfile.read())
#             readfile.close()
#     return filesIN,trainingRC

def randomFiles(files, count,trainingRC):
    trainingCl=[]
    testingCl=[]
    trainingFiles=[]
    testingFiles=[]
    j=0
    for i in range(0,count): 
        j=0
        while(files[j] in trainingFiles):
            j=random.randrange(0, 13) 
        trainingFiles.append(files[j])
        trainingCl.append(trainingRC[j])
                          
    for i in range(0,len(files)):
        if j not in trainingFiles:
            testingFiles.append(files[i])
            testingCl.append(trainingRC[i]) 
    return trainingFiles,testingFiles,trainingCl,testingCl
trainingCl=[]
testingCl=[]
trainingFiles=[]
testingFiles=[]
trainingRC=[]

appleFiles,trainingRC= readSets(fileList)

resulstA=[]
resulstK=[]
maxres=0
kmax=0
stop_words=['and','or','for','with','.',',','the','then','end','is','to','in','it','or','.','this','which','of','a','an',
            'if','as']
for i in range(1,7): 
    trainingFiles,testingFiles,trainingCl,testingCl= randomFiles(appleFiles,9,trainingRC) 
#     count_vect = CountVectorizer(stop_words)
#     print trainingFiles[1]
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


# In[6]:

from os import listdir
import os
from os.path import isfile, join
mypath='E:\\SD201-data-mining\\dataset\\'
onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]
print onlyfiles
pathf=mypath + onlyfiles[1]
print pathf
newf=pathf+'_class_applefruits'
os.rename(pathf,newf)


# In[33]:

# read files(name) from a folder in windows
from os import walk
mypath='E:\\SD201-data-mining\\apple\\'
f = []
for (dirpath, dirnames, filenames) in walk(mypath):
    f.extend(filenames)
    break
print 'filename:', f
print dirpath
print dirnames

# from os import listdir
# from os.path import isfile, join
# mypath='E:\\SD201-data-mining\\apple\\'
# onlyfiles = [f for f in listdir(mypath) if isfile(join(mypath, f))]
# print onlyfiles


# In[ ]:



