
package com.mycompany.desktophotelreservationsystem;
public class Amenity {
double price;
String name;
int number;
Amenity(String s,double p,int no)
{
    name=s;
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
    return this.name;
}
void setName(String s)
{
    this.name=s;
}
void setNumber(int n)
{
    this.number=n;
}
void setPrice(double p){
    this.price=p;
}
}
