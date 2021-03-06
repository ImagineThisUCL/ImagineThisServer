import React, {
    Component
} from "react"
import {
    StyleSheet,
    Image,
    TouchableOpacity,
    View,
} from "react-native"
import propTypes from 'prop-types'

import base from "../../assets/BaseStyle.js"

const styles = StyleSheet.create({
    wrapper: {
        flexDirection: "row",
    },
})

/*
 * A clickable ImageButton with animated opacity upon touch
 *
 * (An alternative to IconButton with native animations)
*/

export default class Button extends Component {
    render() {
        return (
            <View style={styles.wrapper}>
                <TouchableOpacity
                    {...this.props}
                    style={this.props.style}>
                    <Image
                        style={this.props.imageStyle}
                        source={{uri: this.props.imageSrc}}>
                    </Image>
                </TouchableOpacity>
            </View>
        )
    }
}

Button.propTypes = {
    imageSrc: propTypes.node.isRequired,
    style: propTypes.object, // container styles
    imageStyle: propTypes.object, // image styles
}
