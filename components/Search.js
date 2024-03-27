import React, { useState } from "react";
import { View, Button, StyleSheet, Text } from "react-native";
import { GooglePlacesAutocomplete } from "react-native-google-places-autocomplete";
import axios from "axios";

const Search = () => {
  const [fromTown, setFromTown] = useState("");
  const [toTown, setToTown] = useState("");
  const [fromArea, setFromArea] = useState("");
  const [toArea, setToArea] = useState("");
  const [fromSection, setFromSection] = useState("");
  const [toSection, setToSection] = useState("");
  const [searchResults, setSearchResults] = useState();
  const [showResults, setShowResults] = useState(false);

  const handleFromTownSelect = (data) => {
    setFromTown(data.description);
  };

  const handleFromAreaSelect = (data) => {
    setFromArea(data.description);
  };

  const handleFromSectionSelect = (data) => {
    setFromSection(data.description);
  };

  const handleToTownSelect = (data) => {
    setToTown(data.description);
  };

  const handleToAreaSelect = (data) => {
    setToArea(data.description);
  };

  const handleToSectionSelect = (data) => {
    setToSection(data.description);
  };

  const handleFilter = async () => {
    try {
      const response = await axios.get(
        "https://403c-146-141-180-96.ngrok-free.app/search",
        {
          params: {
            fromTown: fromTown,
            toTown: toTown,
            fromArea: fromArea,
            toArea: toArea,
            fromSection: fromSection,
            toSection: toSection,
          },
        }
      );

      console.log("Response from API:", response);

      if (response.status !== 200) {
        throw new Error("Failed to fetch data");
      }

      const data = response.data;
      console.log("Data from API:", data);
      setSearchResults(data);
      setShowResults(true);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <View style={{ flex: 1 }}>
      <GooglePlacesAutocomplete
        placeholder="From Town"
        fetchDetails={true}
        onPress={handleFromTownSelect}
        query={{
          key: "AIzaSyA-Zr1lJxQs21Y0qYX8srSbA4nhJKaJPXc",
          language: "en",
          types: "address",
        }}
        styles={textInputStyles}
      />
      <GooglePlacesAutocomplete
        placeholder="To Town"
        fetchDetails={true}
        onPress={handleToTownSelect}
        query={{
          key: "AIzaSyA-Zr1lJxQs21Y0qYX8srSbA4nhJKaJPXc",
          language: "en",
          types: "address",
        }}
        styles={textInputStyles}
      />

      <GooglePlacesAutocomplete
        placeholder="From Area"
        fetchDetails={true}
        onPress={handleFromAreaSelect}
        query={{
          key: "AIzaSyA-Zr1lJxQs21Y0qYX8srSbA4nhJKaJPXc",
          language: "en",
          types: "address",
        }}
        styles={textInputStyles}
      />
      <GooglePlacesAutocomplete
        placeholder="To Area"
        fetchDetails={true}
        onPress={handleToAreaSelect}
        query={{
          key: "AIzaSyA-Zr1lJxQs21Y0qYX8srSbA4nhJKaJPXc",
          language: "en",
          types: "address",
        }}
        styles={textInputStyles}
      />

      <GooglePlacesAutocomplete
        placeholder="From Section"
        fetchDetails={true}
        onPress={handleFromSectionSelect}
        query={{
          key: "AIzaSyA-Zr1lJxQs21Y0qYX8srSbA4nhJKaJPXc",
          language: "en",
          types: "address",
        }}
        styles={textInputStyles}
      />

      <GooglePlacesAutocomplete
        placeholder="To Section"
        fetchDetails={true}
        onPress={handleToSectionSelect}
        query={{
          key: "AIzaSyA-Zr1lJxQs21Y0qYX8srSbA4nhJKaJPXc",
          language: "en",
          types: "address",
        }}
        styles={textInputStyles}
      />

      <Button title="Search" onPress={handleFilter} />

      {showResults && (
        <View>
          {searchResults.map((result, index) => (
            <View key={index} data={result}>
              <Text>
                From: {result.fromTown}, {result.fromArea}, {result.fromSection}
              </Text>
              <Text>
                To: {result.toTown}, {result.toArea}, {result.toSection}
              </Text>
            </View>
          ))}
        </View>
      )}
    </View>
  );
};

const textInputStyles = StyleSheet.create({
  textInputContainer: {
    backgroundColor: "rgba(0,0,0,0)",
    borderTopWidth: 1,
    borderBottomWidth: 1,
    borderLeftWidth: 1,
    borderRightWidth: 1,
    borderColor: "#ccc",
    marginLeft: 15,
    marginRight: 60,
    paddingLeft: 15,
    paddingRight: 10,
    marginBottom: 0,
    marginTop: 30,
    width: "100%",
    alignItems: "center",
  },
  textInput: {
    marginLeft: 0,
    marginRight: 0,
    height: 30,
    color: "grey",
    fontSize: 14,
    width: "90%",
  },
});

export default Search;
