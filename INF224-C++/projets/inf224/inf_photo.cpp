#include "inf_photo.h"
using namespace std;


double Inf_Photo::getLatitude() const
{
    return latitude;
}

void Inf_Photo::setLatitude(double latitude)
{
    this->latitude = latitude;
}

void Inf_Photo::display(ostream &out) const
{
    Inf_multimedia::display(out);
    out << "The latitude of " << this->getMedia() << " is: " << this->latitude << ". ";
    out << "The longitude of " << this->getMedia() << " is: " << this->longitude << ". ";

}

void Inf_Photo::play() const
{
    string pathComplete = "imagej " + this->getPathMedia() + this->getMedia() + " &";
    const char * path = pathComplete.c_str();
    system(path);
}

Inf_Photo::~Inf_Photo()
{
    cout << "The Photo instance of " << this->getMedia() << " is destroyed!" << endl;
}


double Inf_Photo::getLongitude() const
{
    return longitude;
}

void Inf_Photo::setLongitude(double longitude)
{
    this->longitude = longitude;
}
