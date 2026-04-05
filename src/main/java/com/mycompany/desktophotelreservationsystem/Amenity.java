
package com.mycompany.desktophotelreservationsystem;
public class Amenity {
double price;
String Name;
int number;
Amenity(String s,double p,int no)
{
    Name=s;
    price=p;
    number=no;
}
double getprice(){
    return this.price;
}
int getNumber()
{
    return this.number;
}
String getName()
{
    return this.Name;
}
void setName(String s)
{
    this.Name=s;
}
void setNumber(int n)
{
    this.number=n;
}
void setPrice(double p){
    this.price=p;
}
}
