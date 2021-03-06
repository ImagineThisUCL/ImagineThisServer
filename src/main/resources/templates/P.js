import React, { Component } from "react"
import { StyleSheet, Text } from "react-native"
import propTypes from 'prop-types'

import base from "../../assets/BaseStyle.js"

const styles = StyleSheet.create({
    default: {
        marginBottom: base.margin,
        fontSize: base.font.smallest
    },
    noMargin: {
        marginBottom: 0
    },
    smallMargin: {
        marginBottom: base.margin / 2
    },
    topMargin: {
        marginTop: base.margin
    },
    smallFont: {
        fontSize: base.font.small
    },
    mediumFont: {
        fontSize: base.font.medium
    },
    largeFont: {
        fontSize: base.font.large
    },
    heading: {
        fontSize: base.font.large,
        color: base.colors.blueHeadings
    },
    smallHeading: {
        fontSize: base.font.small,
        color: base.colors.blueHeadings,
        marginBottom: base.margin / 3
    },
    largeHeading: {
        fontSize: base.font.heading,
        color: base.colors.white,
        paddingHorizontal: base.padding,
        marginBottom: base.margin * 1.5
    },
    textCenter: {
        textAlign: "center"
    },
    secondary: {
        color: base.colors.blueSecondary
    },
    grey: {
        color: base.colors.grey
    },
    dark: {
        color: base.colors.darkSecondary
    },
    white: {
        color: base.colors.white
    },
    bottomSeparator: {
        borderBottomWidth: 1,
        borderBottomColor: base.colors.greyLight,
        paddingBottom: base.padding / 2
    }
})

/*
 * Highly flexible text/paragraph component
 *
 * Contains many preset and customisable styles - simply add them as props
*/

export default class P extends Component {
    render() {
        let styleList = [styles.default]
        if (this.props.noMargin) styleList.push(styles.noMargin)
        if (this.props.topMargin) styleList.push(styles.topMargin)
        if (this.props.smallMargin) styleList.push(styles.smallMargin)
        if (this.props.smallFont) styleList.push(styles.smallFont)
        if (this.props.mediumFont) styleList.push(styles.mediumFont)
        if (this.props.largeFont) styleList.push(styles.largeFont)
        if (this.props.heading) styleList.push(styles.heading)
        if (this.props.smallHeading) styleList.push(styles.smallHeading)
        if (this.props.largeHeading) styleList.push(styles.largeHeading)
        if (this.props.textCenter) styleList.push(styles.textCenter)
        if (this.props.secondary) styleList.push(styles.secondary)
        if (this.props.red) styleList.push(styles.red)
        if (this.props.grey) styleList.push(styles.grey)
        if (this.props.dark) styleList.push(styles.dark)
        if (this.props.white) styleList.push(styles.white)
        if (this.props.bottomSeparator) styleList.push(styles.bottomSeparator)

        return (
            <Text {...this.props} style={[...styleList, this.props.style]}/>
        )
    }
}

P.propTypes = {
    children: propTypes.node.isRequired, // paragraph needs content
    style: propTypes.object, // override styles
    noMargin: propTypes.bool,
    topMargin: propTypes.bool,
    smallMargin: propTypes.bool,
    smallFont: propTypes.bool,
    mediumFont: propTypes.bool,
    largeFont: propTypes.bool,
    heading: propTypes.bool,
    smallHeading: propTypes.bool,
    largeHeading: propTypes.bool,
    textCenter: propTypes.bool,
    secondary: propTypes.bool,
    red: propTypes.bool,
    grey: propTypes.bool,
    dark: propTypes.bool,
    white: propTypes.bool,
    bottomSeparator: propTypes.bool,
}
