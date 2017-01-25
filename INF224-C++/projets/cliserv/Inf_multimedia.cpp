
# include <iostream>
#include "Inf_multimedia.h"
using namespace std;


Inf_multimedia::Inf_multimedia(){
    this->media = "" ;
    this->pathMedia = "" ;

}

Inf_multimedia::Inf_multimedia(string media, string pathMedia){
    this->media = media ;
    this->pathMedia = pathMedia ;

}

string Inf_multimedia::getMedia() const{
    return this -> media ;

}

void Inf_multimedia::setMedia(string media){
    this->media = media ;

}

string Inf_multimedia::getPathMedia() const{
    return this->pathMedia ;

}

void Inf_multimedia::setPathMedia(string pathMedia){
    this->pathMedia = pathMedia ;

}

void Inf_multimedia::display(ostream &out) const{

    out << this->media << ". ";
    out << "The path of " << this->media << " is: " << this->pathMedia << ". ";


}

void Inf_multimedia::play() const
{

}

Inf_multimedia::~Inf_multimedia()
{

}

