import '../../node_modules/@testing-library/jest-dom/'

import React from "react";
import { render } from '@testing-library/react';
import Homepage from './Homepage'

test('Homepage should match snapshot',()=>{
    const { asFragment} = render(<Homepage/>);
    expect(asFragment()).toMatchSnapshot();
})