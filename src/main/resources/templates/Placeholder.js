import { View, StyleSheet, Text } from "react-native"
import React, { Component } from "react"
import { StatusBar } from 'expo-status-bar'

import base from "../../assets/BaseStyle.js"

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
    },
    text: {
        marginBottom: 20,
        fontSize: 20,
        textAlign: "center"
    }
})

class Placeholder extends Component {
    render() {
        return (
            <View style={styles.container}>
                <StatusBar style="auto" />
                <Text style={styles.text}>
                    Placeholder view
                </Text>
            </View>
        )
    }
}
export default Placeholder
