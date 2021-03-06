/*
	Double-sided DVD
*/
#include <string>
#include <sstream>
#include <vector>

#include "D_Dvd.h"
#include "Dvd.h"

using std::string;
using std::stringstream;
using std::vector;


D_Dvd::D_Dvd(string id, string title, string format, string audio, int duration, string language, string price, string aspect, Packaging packaging, vector<string> subTracks, vector<string> audTracks)
	: Disk(id, title, format, audio, duration, language, price, aspect, packaging, subTracks, audTracks)
{
}

D_Dvd::~D_Dvd()
{
}

string D_Dvd::details()
{
	stringstream s;
	string subs;
	string auds;

	s << Material::details();

	subs += " Subtitles:";
	for (auto i : subTracks_)
		subs += " ", subs += i;

	auds += " Audio Tracks:";
	for (auto i : audioTracks_)
		auds += " ", auds += i;

	s << subs << ", " << auds
		<< " Side two: "
		<< "ID: " << id_ << ", "
		<< "Title: " << title_ << ", "
		<< "Format: " << format_ << ", "
		<< "Audio: " << audio_ << ", "
		<< "Duration: " << duration_ << ", "
		<< "Language: " << language_ << ", "
		<< "Price: " << price_ << ", "
		<< "Aspect: " << aspect_;

	return s.str();
}

D_Dvd& D_Dvd::SetSideTwoData(string id, string title, string format, string audio, int duration, string language, string aspect)
{
	id_ = id;
	title_ = title;
	format_ = format;
	audio_ = audio;
	duration_ = duration;
	language_ = language;
	aspect_ = aspect;

	return *this;
}

void D_Dvd::GetSideTwoData(string & id, string & title, string & format, string & audio, int & duration, string & language, string & aspect)
{
	id = id_;
	title = title_;
	format = format_;
	audio = audio_;
	duration = duration_;
	language = language_;
	aspect = aspect_;
}
