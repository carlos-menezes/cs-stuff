/*
	Material
*/
#ifndef MATERIAL_H
#define MATERIAL_H

#include <string>
#include <vector>

#include "Packaging.h"

using std::string;
using std::vector;


class Material
{
public:
	virtual ~Material(){};
	virtual string details();

	string GetId();
	string GetTitle();
	string GetFormat();
	string GetAudio();
	int GetDuration();
	string GetLanguage();
	string GetPrice();
	string GetAspect();
	Packaging GetPackaging();

protected:
	Material(
		string id_,
		string title_,
		string format_,
		string audio_,
		int duration_,
		string language_,
		string price_,
		string aspect_,
		Packaging packaging_);
		
	string id_;
	string title_;
	string format_;
	string audio_;
	int duration_;
	string language_;
	string price_;
	string aspect_;
	Packaging packaging_;
};

#endif
