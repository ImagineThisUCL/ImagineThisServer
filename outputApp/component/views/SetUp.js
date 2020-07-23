import { 
    StyleSheet, 
    View, 
    Button } from "react-native"
import React, { Component } from "react"
import P from '../reusables/P'

const styles = StyleSheet.create({
  view: {
    flex: 1,
    padding: 10,
    backgroundColor: "#11287B"
  },
  p: {
    marginBottom: 20,
    fontSize: 22,
    color: "#f2f2f2"
  },
  buttonWrapper: {
      flexDirection: "row",
      justifyContent: "space-between"
  },
  button: {
      fontSize: 28
  }
})

class SetUp extends Component {render() {
        return (
            <View style={styles.view}>
 <P fontSize ={24.0} textCenter = {true}>CarerCare V0120-21: Set Up</P>
 <P fontSize ={24.0} textCenter = {true}>A carer support worker will have recommended this application to you.  They recognise that carers often forget to look after themselves.  This app has been designed to encourage you to do this.

Occasionally it will nudge you to keep in contact with people you like to speak to, or persue activities with them.  It will also make you aware of new local groups with similar interests to yourself.    
If you consent to this click
Save</P>
  </View>
        )
    }
}
export default SetUp