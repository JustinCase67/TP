// File: context/TableauContext.js
import React, { createContext, useState } from 'react';

export const TableauContext = createContext();

export const TableauProvider = ({ children }) => {
  const [tableau, setTableau] = useState([]);
  return (
    <TableauContext.Provider value={{ tableau, setTableau }}>
      {children}
    </TableauContext.Provider>
  );
};

// File: styles/GlobalStyles.js
import { StyleSheet } from 'react-native';

export default StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    padding: 16,
    backgroundColor: '#fff',
  },
  button: {
    backgroundColor: '#007AFF',
    paddingVertical: 12,
    paddingHorizontal: 24,
    borderRadius: 8,
    marginVertical: 8,
  },
  buttonText: {
    color: '#fff',
    fontSize: 16,
    textAlign: 'center',
  },
  input: {
    borderWidth: 1,
    borderColor: '#ccc',
    padding: 8,
    borderRadius: 5,
    width: '100%',
    marginVertical: 8,
  },
  label: {
    fontSize: 16,
    alignSelf: 'flex-start',
    marginTop: 8,
  },
});

// File: app/_layout.js
import React from 'react';
import { TableauProvider } from '../context/TableauContext';
import { Slot } from 'expo-router';

export default function Layout() {
  return (
    <TableauProvider>
      <Slot />
    </TableauProvider>
  );
}

// File: app/index.js (Home screen)
import React from 'react';
import { View, Image, TouchableOpacity, Text } from 'react-native';
import { Link } from 'expo-router';
import GlobalStyles from '../styles/GlobalStyles';

export default function Home() {
  return (
    <View style={GlobalStyles.container}>
      <Image
        source={require('../assets/festive.png')}
        style={{ width: 200, height: 200, marginBottom: 24 }}
        resizeMode="contain"
      />
      <TouchableOpacity style={GlobalStyles.button}>
        <Link href="/add" style={GlobalStyles.buttonText}>Ajouter un cadeau</Link>
      </TouchableOpacity>
      <TouchableOpacity style={GlobalStyles.button}>
        <Link href="/list" style={GlobalStyles.buttonText}>Voir les suggestions</Link>
      </TouchableOpacity>
    </View>
  );
}

// File: app/add.js (AddGift screen)
import React, { useState, useContext } from 'react';
import { View, Text, TextInput, TouchableOpacity, Alert } from 'react-native';
import Slider from '@react-native-community/slider';
import { TableauContext } from '../context/TableauContext';
import GlobalStyles from '../styles/GlobalStyles';

export default function AddGift() {
  const { tableau, setTableau } = useContext(TableauContext);
  const [destinataire, setDestinataire] = useState('');
  const [cadeau, setCadeau] = useState('');
  const [prix, setPrix] = useState(1);
  const [desirabilite, setDesirabilite] = useState(1);

  const ajouterCadeau = () => {
    if (!destinataire || !cadeau) {
      Alert.alert('Erreur', 'Veuillez remplir tous les champs.');
      return;
    }
    const nouvelObjet = { id: tableau.length + 1, destinataire, cadeau, prix, desirabilite };
    setTableau([...tableau, nouvelObjet]);
    setDestinataire('');
    setCadeau('');
    setPrix(1);
    setDesirabilite(1);
    Alert.alert('Succès', 'Suggestion de cadeau ajoutée !');
  };

  return (
    <View style={GlobalStyles.container}>
      <Text style={GlobalStyles.label}>Destinataire :</Text>
      <TextInput
        style={GlobalStyles.input}
        placeholder="Ex: Ma copine"
        value={destinataire}
        onChangeText={setDestinataire}
      />
      <Text style={GlobalStyles.label}>Cadeau possible :</Text>
      <TextInput
        style={GlobalStyles.input}
        placeholder="Ex: Livre"
        value={cadeau}
        onChangeText={setCadeau}
      />
      <Text style={GlobalStyles.label}>Prix (1 = Abordable, 5 = Très cher) : {prix}</Text>
      <Slider
        style={{ width: '100%', height: 40 }}
        minimumValue={1}
        maximumValue={5}
        step={1}
        value={prix}
        onValueChange={setPrix}
      />
      <Text style={GlobalStyles.label}>Désirabilité (1 = Pas aimé, 5 = Adoré) : {desirabilite}</Text>
      <Slider
        style={{ width: '100%', height: 40 }}
        minimumValue={1}
        maximumValue={5}
        step={1}
        value={desirabilite}
        onValueChange={setDesirabilite}
      />
      <TouchableOpacity style={GlobalStyles.button} onPress={ajouterCadeau}>
        <Text style={GlobalStyles.buttonText}>Ajouter</Text>
      </TouchableOpacity>
    </View>
  );
}

// File: app/list.js (GiftList screen)
import React, { useContext } from 'react';
import { View, Text, FlatList } from 'react-native';
import { TableauContext } from '../context/TableauContext';
import GlobalStyles from '../styles/GlobalStyles';

export default function GiftList() {
  const { tableau } = useContext(TableauContext);

  return (
    <View style={GlobalStyles.container}>
      {tableau.length === 0 ? (
        <Text>Aucune suggestion pour l'instant.</Text>
      ) : (
        <FlatList
          data={tableau}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <View style={{ width: '100%', marginVertical: 8, padding: 12, borderWidth: 1, borderColor: '#ccc', borderRadius: 5 }}>
              <Text><Text style={{ fontWeight: 'bold' }}>Destinataire :</Text> {item.destinataire}</Text>
              <Text><Text style={{ fontWeight: 'bold' }}>Cadeau :</Text> {item.cadeau}</Text>
              <Text><Text style={{ fontWeight: 'bold' }}>Prix :</Text> {item.prix}/5</Text>
              <Text><Text style={{ fontWeight: 'bold' }}>Désirabilité :</Text> {item.desirabilite}/5</Text>
            </View>
          )}
          style={{ width: '100%' }}
        />
      )}
    </View>
  );
}

// File: package.json (partial)
{
  "dependencies": {
    "expo": "~48.0.0",
    "expo-router": "^1.0.0",
    "react": "18.2.0",
    "react-native": "0.71.8",
    "@react-native-community/slider": "^4.2.4"
  }
}
