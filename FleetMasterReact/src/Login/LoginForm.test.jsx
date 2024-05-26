import '../../node_modules/@testing-library/jest-dom/'

import React from "react";
import { render } from '@testing-library/react';
import LoginForm from './LoginForm';

test('LoginForm should match snapshot',()=>{
    const { asFragment } = render(<LoginForm/>);
    expect(asFragment()).toMatchSnapshot();
});
